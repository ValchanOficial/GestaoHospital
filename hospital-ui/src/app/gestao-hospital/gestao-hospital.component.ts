import { Component, OnInit } from '@angular/core';
import { HospitalService } from '../hospital.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-gestao-hospital',
  templateUrl: './gestao-hospital.component.html',
  styleUrls: ['./gestao-hospital.component.css']
})
export class GestaoHospitalComponent implements OnInit {

  title = 'Sistema de Gestão Hospitalar';
  hospital = {};
  hospitais = [];

  constructor(
    private hospitalService: HospitalService,
    private messageService: MessageService
    ) { }

  ngOnInit() {
    this.consultar();
   }
  consultar() {
    this.hospitalService.listarHospitais()
    .subscribe(resposta => this.hospitais = resposta as any);
  }
  adicionar() {
    this.hospitalService.adicionar(this.hospital)
    .subscribe(() => {
      this.hospital = {};
      this.consultar();
      this.messageService.add({
        severity: 'success',
        summary: 'Hospital adicionado com sucesso!'
      });
    },
    resposta => {
      let msg = 'Erro inesperado. Tente novamente.';
      if (resposta.error.message) {
        msg = resposta.error.message;
      }
      this.messageService.add({
        severity: 'error',
        summary: msg
      });
    });
  }
}
