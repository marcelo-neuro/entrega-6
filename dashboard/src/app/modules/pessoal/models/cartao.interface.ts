export interface Cartao {
  id: number;
  numero: string;
  cvv: string;
  tipoCartao: 'CREDITO' | 'DEBITO'; // se houver outros tipos, adicione aqui
  vencimento: string; // formato ISO, pode ser convertido para Date se preferir
  clienteId: number;
}
