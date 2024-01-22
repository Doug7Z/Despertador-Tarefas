package application.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import application.DataBaseSimul.DataSimul;
import application.Entity.Tarefas;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;



public class TarefasController {
	
	private static Long gerarIds = 1L;
	
	private static final DateTimeFormatter horaFormatada = DateTimeFormatter.ofPattern("HH:mm");
	
		
	public void cadastrarTarefa(String descricao, LocalTime horaDespertar) {
		Tarefas novaTarefa = new Tarefas();

		novaTarefa.setId(new SimpleLongProperty(gerarIds++));
        novaTarefa.setDescricao(new SimpleStringProperty(descricao));
        novaTarefa.setHoraDespertar(new SimpleStringProperty(horaDespertar.format(horaFormatada)));
		
	
		DataSimul.cadastrarTarefa(novaTarefa);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
