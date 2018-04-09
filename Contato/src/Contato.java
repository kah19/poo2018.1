import java.util.ArrayList;

public class Contato {
	String nome;
	ArrayList<Telefone> telefones;
	
	public Contato(String nome) {
		this.nome = nome;
		this.telefones = new ArrayList<Telefone>(null);
	}
	
	public String toString() {
		return " " + this.nome + " " + this.telefones;
	}
	
	public boolean addFone(Telefone telefones) throws Exception {
		for (Telefone fone : this.telefones) {
			if (fone.getFoneId().equals(telefones.foneId)) {
				throw new Exception("Telefone existente");
			}
		}
		
		return this.telefones.add(telefones);
	}
	
	public boolean rmFone(String foneId) throws Exception{
		for (Telefone telefone : telefones) {
			if (telefone.getFoneId().equals(foneId)) {
				return this.telefones.remove(telefone);
			}
		}
		throw new Exception("Telefone nao existe");
	}
}

class Telefone{
	String foneId;
	int numero;
	
	public Telefone(String foneId, int numero) {
		this.foneId = foneId;
		this.numero = numero;
	}
	
	public String toString() {
		return "" + this.foneId + ":" + this.numero;
	}
	
	public String getFoneId() {
		return foneId;
	}
	
}

class Controller{
    Contato contato;
   
    public Controller() {
        contato = new Contato("");
    }
    
    public String oracle(String line) throws Exception {
        String ui[] = line.split(" ");

		if(ui[0].equals("help"))
        	return "init_contato, show, addFone_id_num, rmFone_id";
        else if(ui[0].equals("show"))
            return " " + contato;
        else if(ui[0].equals("init"))
            contato = new Contato(ui[1]);
        else if(ui[0].equals("addFone"))
        	 contato.addFone(new Telefone(ui[1], Integer.parseInt(ui[2])));
        else if(ui[0].equals("rmFone")) 
        	contato.rmFone(ui[1]);
        	else
            return "comando invalido";
        return "done";
    }
}


