package application;
	
import java.io.InputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import application.Controller.TarefasController;
import application.DataBaseSimul.DataSimul;
import application.Entity.Tarefas;
import application.midia.Midia;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;


public class Main extends Application {
	
	TarefasController tarefasController = new TarefasController();
	private static boolean alarmeAtivo = true;
	ObservableList<Tarefas> tarefaObs = FXCollections.observableArrayList();
	private TextField descricaoTarefa;

	     
     private void pararAlarme() {
         alarmeAtivo = false;

         ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
         executorService.schedule(() -> {
             alarmeAtivo = true;
             System.out.println("Alarme reativado.");
         }, 2, TimeUnit.SECONDS);

         executorService.shutdown();
     }
     
    	 
    public static void main(String[] args) {
        launch(args);
 
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        root.getStyleClass().add("Vbox");

        Label descricaoLabel = new Label("Descrição da tarefa:");
        TextField descricaoField = new TextField();        
     
        
        Label horaDespertarLabel = new Label("Hora para despertar (HH:mm):");
//        TextField horaDespertarField = new TextField();
        
        TextField horafield = new TextField();
        TextField minutofield = new TextField();
        
        descricaoLabel.getStyleClass().add("descricao-label");
        descricaoField.getStyleClass().add("descricao-field");
        horaDespertarLabel.getStyleClass().add("despertar-label");
//        horaDespertarField.getStyleClass().add("despertar-field");
        horafield.getStyleClass().add("horaField");
        minutofield.getStyleClass().add("minutoField");
        
       
//        Label descriTarefa = new Label("Tarefa");
        descricaoTarefa = new TextField();       
        descricaoTarefa.getStyleClass().add("descricao-tarefa-field");
        
//        descriTarefa.getStyleClass().add("descricao-tarefa-label");
        
        
        TableView<Tarefas> tabela = new TableView<>();
        tabela.setMinWidth(500);
        TableColumn<Tarefas, String> colunaDescricao = new TableColumn<>("Descrição");
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        TableColumn<Tarefas, String> colunaHoraDespertar = new TableColumn<>("Alarme");
        colunaHoraDespertar.setCellValueFactory(new PropertyValueFactory<>("horaDespertar"));

        tabela.getStyleClass().add("tabela");
        colunaDescricao.getStyleClass().add("col-descricao");
        colunaHoraDespertar.getStyleClass().add("col-despertar");
        
        tabela.getColumns().addAll(colunaDescricao, colunaHoraDespertar);
	    

        Button cadastrarButton = new Button("Cadastrar");
        cadastrarButton.setOnAction(event -> {
        	
            String descricao = descricaoField.getText();
//            String horaDespertarString = horaDespertarField.getText();
            
            String hora = horafield.getText();
            String minuto = minutofield.getText();
            
            String horaMinuto = hora + ":" + minuto;
            
//            LocalTime horaDespertar = LocalTime.parse(horaDespertarString);
            LocalTime horaDespertar = LocalTime.parse(horaMinuto);
            
            
            tarefasController.cadastrarTarefa(descricao, horaDespertar);
            
            Tarefas novaTarefa = new Tarefas();
            novaTarefa.setDescricao(descricao);
            novaTarefa.setHoraDespertar(horaDespertar.toString());

            
            tarefaObs.add(novaTarefa);

            descricaoField.clear();
//            horaDespertarField.clear();
            horafield.clear();
            minutofield.clear();
            
                           
//            System.out.print("TarefaOBS " + tarefaObs);
        });
        tabela.setItems(tarefaObs);
        
        cadastrarButton.getStyleClass().add("btn-cadastrar");

   
        Button pararAlarmeButton = new Button("Parar Alarme");
	        pararAlarmeButton.setOnAction(event1 -> {
	        pararAlarme();	        
	        
	        List<Tarefas> tarefasList = DataSimul.getTarefas();
	        descricaoTarefa.clear();
	        tarefasList.remove(0);
	        for(Tarefas taf : tarefasList) {
	        	System.out.print(taf);
	        	
	        }	        
        });
	        
	    pararAlarmeButton.getStyleClass().add("btn-pararAlarme");    
        
        
        root.getChildren().addAll(descricaoLabel, descricaoField, horaDespertarLabel, horafield, minutofield, cadastrarButton, descricaoTarefa,pararAlarmeButton,tabela);
        
        Scene scene = new Scene(root, 700, 600);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setTitle("Cadastro de Tarefas");
        primaryStage.setScene(scene);
        primaryStage.show();

        Thread despertadorThread = new Thread(new DespertadorRunnable(descricaoTarefa)); 
        despertadorThread.setDaemon(true);
        despertadorThread.start();
    }
         
    private static class DespertadorRunnable implements Runnable {
    	private final TextField descricaoTarefa;
    	
    	public DespertadorRunnable(TextField descricaoTarefa) {
            this.descricaoTarefa = descricaoTarefa; 
        }
    	
    	
        @Override
        public void run() {
            while (true) {
                if (alarmeAtivo) {
                    List<Tarefas> tarefasList = DataSimul.getTarefas();            
                    Iterator<Tarefas> iterator = tarefasList.iterator();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");                    
                    LocalTime horaAtual = LocalTime.now();
                    String horaAtualString = horaAtual.format(formatter);
                                
                  while (iterator.hasNext()) {
                      Tarefas tarefa = iterator.next();
                      if (horaAtualString.equals(tarefa.getHoraDespertar())) {                    	  
                    	  Midia.midiaplayer();                    	  
//                    	  System.out.println("DESPERTANDO para a tarefa: " + tarefa.getDescricao());                     	                      
                    	  Platform.runLater(() -> {
                              descricaoTarefa.setText(tarefa.getDescricao());
                          });                    	                      	  
                    }      
  
                  }
                    try {
                        Thread.sleep(5000); 
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
  
}
