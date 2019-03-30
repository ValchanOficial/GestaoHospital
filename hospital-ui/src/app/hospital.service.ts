import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HospitalService {

  apiUrl = 'http://localhost:8080/v1/hospitais/';

  constructor(private httpClient: HttpClient) { }

  listarHospitais() {
    return this.httpClient.get(this.apiUrl);
  }
  adicionar(hospital: any) {
    return this.httpClient.post(this.apiUrl, hospital);
  }
}
