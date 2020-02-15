package com.jovo.ScienceCenter.repository;

import com.jovo.ScienceCenter.model.ScientificPaper;
import com.jovo.ScienceCenter.model.Status;
import com.jovo.ScienceCenter.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.BitSet;
import java.util.List;


public interface ScientificPaperRepository extends JpaRepository<ScientificPaper, Long>{

    List<ScientificPaper> findByAuthorAndScientificPaperStatus(UserData author, Status scientificPaperStatus);
}
