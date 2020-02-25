package com.jovo.ScienceCenter.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jovo.ScienceCenter.dto.ScientificPaperForSearchResultDTO;
import com.jovo.ScienceCenter.model.MembershipFee;
import com.jovo.ScienceCenter.model.ScientificPaper;
import com.jovo.ScienceCenter.model.UserData;
import com.jovo.ScienceCenter.model.elasticsearch.IndexUnit;
import com.jovo.ScienceCenter.model.elasticsearch.RequiredHighlight;
import com.jovo.ScienceCenter.model.elasticsearch.ResultData;
import com.jovo.ScienceCenter.repository.elasticsearch.ScientificPaperElasticsearchRepository;
import com.jovo.ScienceCenter.service.ResultRetrieverService;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ResultRetrieverServiceImpl implements ResultRetrieverService {
	
	@Autowired
	private ScientificPaperElasticsearchRepository scientificPaperElasticsearchRepository;

	@Autowired
	private ScientificPaperService scientificPaperService;

	@Autowired
	private UserService userService;

	@Autowired
	private MembershipFeeService membershipFeeService;

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");


	@Override
	public List<ResultData> getResults(org.elasticsearch.index.query.QueryBuilder query,
									   List<RequiredHighlight> requiredHighlights) {
		if (query == null) {
			return null;
		}
			
		List<ResultData> results = new ArrayList<ResultData>();

		ScientificPaper scientificPaper;
		ScientificPaperForSearchResultDTO scientificPaperForSearchResultDTO;
		ResultData result;

        for (IndexUnit indexUnit : scientificPaperElasticsearchRepository.search(query)) {
        	scientificPaper = scientificPaperService.getScientificPaper(indexUnit.getTitle());
			UserData author = scientificPaper.getAuthor();
			User authorCamundaUser = userService.getUser(author.getCamundaUserId());
			String authorStr = author.getCamundaUserId().concat(" (").concat(authorCamundaUser.getFirstName()).concat(" ")
					.concat(authorCamundaUser.getLastName()).concat(")");

			List<String> coauthors = scientificPaper.getCoauthors().stream()
					.map(c -> c.getFirstName().concat(" ").concat(c.getLastName()))
					.collect(Collectors.toList());

			String coauthorsStr = String.join(", ", coauthors);

			String paidUpTo;

			Long payerId = null;
			try {
				payerId = userService.getLoggedUser().getId();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				MembershipFee membershipFee =
						membershipFeeService.getActivatedMembershipFeeByProductIdAndPayerId(scientificPaper.getId(),
								false, payerId);
				paidUpTo = "Paid up to " + membershipFee.getValidUntil().format(DATE_TIME_FORMATTER);
			} catch (Exception e) {
				paidUpTo = null;
			}

			scientificPaperForSearchResultDTO =
					new ScientificPaperForSearchResultDTO(scientificPaper.getId(), scientificPaper.getMagazineName(),
							scientificPaper.getScientificPaperAbstract(), scientificPaper.getScientificArea().getName(),
							authorStr, coauthorsStr, paidUpTo, scientificPaper.getPlans());
			result = new ResultData(indexUnit.getTitle(), indexUnit.getKeywords(), indexUnit.getFilename(),
									"", scientificPaperForSearchResultDTO);
        	results.add(result);
		}
        
		
		return results;
	}

}
