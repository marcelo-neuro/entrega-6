export interface Transacao {
  id: number;
  valor: number;
  dataTransacao: string; // formato ISO, pode ser convertido para Date se preferir
  descricao: string;
  clienteId: number;
  cartaoId: number;
}