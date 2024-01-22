package application.DataBaseSimul;

import java.util.ArrayList;
import java.util.List;

import application.Entity.Tarefas;



public class DataSimul {
	
private static List<Tarefas> listaTarefas = new ArrayList<>();
	
	public static void cadastrarTarefa(Tarefas tarefas) {
		listaTarefas.add(tarefas);
		
	}
	
	public static List<Tarefas> getTarefas(){
		return listaTarefas;
	}
	
	

}
