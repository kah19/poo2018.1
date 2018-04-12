package junkfood;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

class Espiral{
	String nome;
	int qtd;
	float preco = 0.f;
	
	public Espiral(String nome, int qtd, float preco) {
		this.nome = nome;
		this.qtd = qtd;
		this.preco = preco;
	}
	
	public String toString() {
		return "[" + nome +" : " + qtd + " U : " + preco + " RS]";
	}
}

class Maquina{
	ArrayList<Espiral> espirais;
	static int maxProdutos;
	static Float saldoCliente = 0f;
	static Float lucro = 0f;
	
	public Maquina(int qtdEspirais, int maxProdutos){
		Maquina.maxProdutos = maxProdutos;
		this.espirais = new ArrayList<Espiral>();
		for(int i = 0; i < qtdEspirais; i++)
			this.espirais.add(new Espiral("-", 0, 0));
	}
	public String toString() {
		String saida = "Saldo: " + Maquina.saldoCliente + "\n";
		for(int i = 0; i < espirais.size(); i++)
			saida += i + " " + espirais.get(i) + "\n";
		return saida;
	}
	
	void addProduto(int id, String nome, int qtd, float preco ){	
		for(int i = 0; i < espirais.size(); i++) {
			if((id == i) && (qtd <= Maquina.maxProdutos)) {
				espirais.get(id).nome = nome;
				espirais.get(id).preco = preco;
				espirais.get(id).qtd = qtd;
				return;
			}
			else if(Maquina.maxProdutos < qtd ) {
				throw new RuntimeException("fall: limite de produtos eh "+ Maquina.maxProdutos + " por espiral");
			}
		}
			throw new RuntimeException("fall: Indice " + id +" não existe");
			
	}
		
	void limpar(int id) {
		espirais.get(id).nome = "-";
		espirais.get(id).preco = 0f;
		espirais.get(id).qtd = 0;
	}
	
	void dinheiro(int valor) {
		Maquina.saldoCliente += valor;
	}
	
	void vender(int op) {
		for(int i = 0; i < espirais.size(); i++){
			if(op == i) {
				if((espirais.get(i).qtd > 0) && (Maquina.saldoCliente >= espirais.get(i).preco )) {
					espirais.get(i).qtd -= 1;
					Maquina.saldoCliente -= espirais.get(i).preco;
					Maquina.lucro += espirais.get(i).preco;
					throw new RuntimeException("comprou um " + espirais.get(i).nome + " saldo: " + Maquina.saldoCliente);
				}
				else if(espirais.get(i).qtd <= 0) {
					throw new RuntimeException("fail: espiral sem produtos");
				}
				else {
					throw new RuntimeException("fail: " + espirais.get(i).nome + " valor " + espirais.get(i).preco + ", saldo insuficiente");
				}
			}
		}
		throw new RuntimeException("fail: indice nao existe");
	}
	
	Float troco() {
		Float saida = Maquina.saldoCliente;
		Maquina.saldoCliente = 0f;
		
		return saida;
	}
	
}

	

class Controller{
    Maquina maq;
    
    static final int DEFAULT_ESPIRAIS = 3;
    static final int DEFAULT_MAX = 5;
    public Controller() {
        maq = new Maquina(DEFAULT_ESPIRAIS, DEFAULT_MAX);
    }
    
    //recebe uma string e tenta converter em float
    private float toFloat(String s) {
        return Float.parseFloat(s);
    }
    
    //nossa funcao oraculo que recebe uma pergunta e retorna uma resposta
    public String oracle(String line){
        String ui[] = line.split(" ");

        if(ui[0].equals("help"))
            return "show, init _espirais _maximo, set _ind _nome _qtd _valor, limpar _ind, dinheiro _valor, troco, lucro, comprar _ind ";
        else if(ui[0].equals("init"))
            maq = new Maquina(Integer.parseInt(ui[1]), Integer.parseInt(ui[2]));
        else if(ui[0].equals("show"))
            return "" + maq;
        else if(ui[0].equals("set"))
        	maq.addProduto(Integer.parseInt(ui[1]), ui[2], Integer.parseInt(ui[3]), Float.parseFloat(ui[4]));
        else if(ui[0].equals("limpar"))
        	maq.limpar(Integer.parseInt(ui[1]));
        else if(ui[0].equals("dinheiro"))
        	maq.dinheiro(Integer.parseInt(ui[1]));
        else if(ui[0].equals("troco"))
        	return "" + maq.troco();
        else if(ui[0].equals("lucro"))
        	return "" + Maquina.lucro;
        else if(ui[0].equals("comprar"))
        	maq.vender(Integer.parseInt(ui[1]));
        else
            return "comando invalido";
        return "done";
    }

}


