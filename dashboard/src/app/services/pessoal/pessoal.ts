import { Injectable } from '@angular/core';
import { Pessoal } from '../../modules/pessoal/models/pessoal.interface';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cliente } from '../../modules/pessoal/models/cliente.interface';
import { Cartao } from '../../modules/pessoal/models/cartao.interface';
import { Transacao } from '../../modules/pessoal/models/transacao.interface';

@Injectable({
  providedIn: 'root',
})
export class PessoalService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  pegarCliente(id: number | null, telefone: string | null, email: string | null): Observable<Cliente> {
  if (telefone) {
    return this.http.get<Cliente>(`${this.apiUrl}/clientes/telefone/${telefone}`);
  } else if (email) {
    return this.http.get<Cliente>(`${this.apiUrl}/clientes/email/${email}`);
  } else if (id !== null || id !== undefined) {
    return this.http.get<Cliente>(`${this.apiUrl}/clientes/id/${id}`);
  } else {
    throw new Error('É necessário informar pelo menos um parâmetro.');
  }
}

  pegarCartoes(id: number): Observable<Cartao> {
    return this.http.get<Cartao>(`${this.apiUrl}/cartoes/clienteId/${id}`, {});
  }

  pegarPagamentos(id: number): Observable<Transacao> {
    return this.http.post<Transacao>(`${this.apiUrl}/pagamentos/clienteId/${id}`, {});
  }

  pegarPagamentoUnico(pagamentoId: number) {
  return this.http.get(`${this.apiUrl}/indicadores/descricao-pagamento/${pagamentoId}`,{
    responseType: 'text' // 👈 isso diz ao Angular que a resposta é texto puro
  });
}
}
