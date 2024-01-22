package application.Entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Tarefas {
	
	private SimpleLongProperty id;
	
	private SimpleStringProperty descricao;
	
	private SimpleStringProperty horaDespertar;
       
	public Tarefas() {
        this.id = new SimpleLongProperty();
        this.descricao = new SimpleStringProperty();
        this.horaDespertar = new SimpleStringProperty();
    }
  

	public long getId() {
        return id.get();
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getDescricao() {
        return descricao.get();
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }

    public String getHoraDespertar() {
        return horaDespertar.get();
    }

    public void setHoraDespertar(String horaDespertar) {
        this.horaDespertar.set(horaDespertar);
    }



	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public void setDescricao(SimpleStringProperty descricao) {
		this.descricao = descricao;
	}

	public void setHoraDespertar(SimpleStringProperty horaDespertar) {
		this.horaDespertar = horaDespertar;
	}

	@Override
	public String toString() {
	    return "Tarefa{" +
	            "id=" + id +
	            ", descricao='" + descricao + '\'' +
	            ", HoraDespertar=" + horaDespertar +
	            '}';
	}
    

}
