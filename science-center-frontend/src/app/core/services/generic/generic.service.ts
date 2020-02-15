import { Injectable, Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class GenericService {

  constructor(private http: HttpClient, @Inject('BASE_API_URL') private baseUrl: string) {}

  getAll<T>(relativeUrl: string): Observable<T[]> {
    return this.http.get<T[]>(this.baseUrl + relativeUrl);
  }

  get<T>(relativeUrl: string): Observable<T> {
    // const params: HttpParams = new HttpParams().set('_id',id);
    return this.http.get<T>(this.baseUrl + relativeUrl);
  }

  getById<T>(relativeUrl: string, id: number): Observable<T> {
    // const params: HttpParams = new HttpParams().set('_id',id);
    return this.http.get<T>(this.baseUrl + relativeUrl + `/${id}`);
  }

  getListById<T>(relativeUrl: string, id: number): Observable<T[]> {
    // const params: HttpParams = new HttpParams().set('_id',id);
    return this.http.get<T[]>(this.baseUrl + relativeUrl + `/${id}`);
  }

  put<T>(relativeUrl: string, t: T): Observable<T> {
    const headers: HttpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.put<T>(this.baseUrl + relativeUrl, t, { headers });
  }

  save<T>(relativeUrl: string, t: T) {
    const headers: HttpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post(this.baseUrl + relativeUrl, t,  { headers });
  }

  post(relativeUrl: string, t: any) {
    const headers: HttpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post(this.baseUrl + relativeUrl, t, { headers });
  }

  sendPaper(relativeUrl: string, file: any, data: any) {
    const fd = new FormData();
    fd.append('scientific_paper_file', file);
    fd.append('scientific_paper_data', new Blob([JSON.stringify(data)], {type: 'application/json'}));
    return this.http.post(this.baseUrl + relativeUrl, fd);
  }

  sendPaperFile(relativeUrl: string, file: any) {
    const fd = new FormData();
    fd.append('scientific_paper_file', file);
    return this.http.put(this.baseUrl + relativeUrl, fd);
  }

  getPdfContentFromServer(relativeUrl: string) {
    const httpOptions = {
      responseType: 'arraybuffer' as 'json'
     };

    return this.http.get(this.baseUrl + relativeUrl, httpOptions);
  }

  delete(relativeUrl: string, id: number) {
    return this.http.delete(this.baseUrl + relativeUrl + `/${id}` + '/delete');
  }

}
