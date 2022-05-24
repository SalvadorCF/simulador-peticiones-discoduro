/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peticionesdisco;

import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author ASUSTEC
 */
public class Comparacion extends javax.swing.JFrame {
 DefaultTableModel modeloP;
 DefaultTableModel modeloReporte;
int contadorP=1;
    /**
     * Creates new form Comparacion
     */
    public Comparacion() {
        initComponents();
        modeloP = new DefaultTableModel();
        modeloP.addColumn("ID");
        modeloP.addColumn("Localidad");
        tablaP.setModel(modeloP);
        
        modeloReporte = new DefaultTableModel();
        modeloReporte.addColumn("FIFO");
        modeloReporte.addColumn("Recorrido");
        modeloReporte.addColumn("SSTF");
        modeloReporte.addColumn("Recorrido");
        modeloReporte.addColumn("SCAN");
        modeloReporte.addColumn("Recorrido");
        modeloReporte.addColumn("C-SCAN");
        modeloReporte.addColumn("Recorrido");
        tablaReporte.setModel(modeloReporte);
        
          tfifo.setEditable(false);
         pfifo.setEditable(false);
         vfifo.setEditable(false);
              tsstf.setEditable(false);
         psstf.setEditable(false);
         vsstf.setEditable(false);
              tscan.setEditable(false);
         pscan.setEditable(false);
         vscan.setEditable(false);
              tcscan.setEditable(false);
         pcscan.setEditable(false);
         vcscan.setEditable(false);
        
    }
  
  
    
    //------------------METODO DE FIFO---------------------//
    public void fifo(){
       
         int ultimo = 0;
        int inicio = Integer.parseInt(txtInicio.getText());
         String reporte = "Orden de numeros: ";
       int estadisticas[]= new int [tablaP.getRowCount()+1];
       estadisticas[0]=inicio;
        
       

        XYSeries series = new XYSeries("Disco Duro");
        
         //Recoleccion de datos
         series.add(0,inicio);
        String datos[][] = new String[tablaP.getRowCount()][2];
        for (int i = 0; i < tablaP.getRowCount(); i++) {
            datos[i][0] = tablaP.getValueAt(i, 0).toString();
            datos[i][1] = tablaP.getValueAt(i, 1).toString();
            int n=Integer.parseInt(datos[i][1]);
            series.add(i+1,n);
            reporte = reporte + n + ",";
            estadisticas[i+1]=n;
            ultimo=n;
        }
        
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
            "Peticiones", // Título
            "Movimientos", // Etiqueta Coordenada X
            "Pistas", // Etiqueta Coordenada Y
            dataset, // Datos
            PlotOrientation.VERTICAL,
            true,
            false,
            false
        );

        // Mostramos la grafica en pantalla
        ChartFrame frame = new ChartFrame("FCFS", chart);
        frame.pack();
        frame.setVisible(true);

        int totalPistas=0;
        int pistas=0;
        int promedio=0;
        int varianza=0;
       for(int i=0; i<estadisticas.length-1;i++){
           totalPistas=totalPistas+Math.abs(estadisticas[i+1]-estadisticas[i]);
           tablaReporte.setValueAt(Math.abs(estadisticas[i+1]-estadisticas[i]), i+1, 1);
           
       }
       promedio= (totalPistas)/(estadisticas.length-1);
      for(int i=0; i<estadisticas.length-1;i++){
           pistas=Math.abs(estadisticas[i+1]-estadisticas[i]);
           varianza=varianza+(pistas-promedio)*(pistas-promedio);
       }
      varianza=varianza/(estadisticas.length-1);
       
      tfifo.setText(String.valueOf(totalPistas));
      pfifo.setText(String.valueOf(promedio));
      vfifo.setText(String.valueOf(varianza));
      
      for(int i=0; i<estadisticas.length;i++){
          tablaReporte.setValueAt(String.valueOf(estadisticas[i]), i, 0);
      }
      
    }
    //------------------FIN METODO DE FIFO-----------------//
    //------------------METODO DE SSTF---------------------//
    public void sstf(){
         int inicio = Integer.parseInt(txtInicio.getText());
        String reporte = "Orden de numeros: ";
       int estadisticas[]= new int [tablaP.getRowCount()+1];
        //Recoleccion de datos
        int datos[] = new int[tablaP.getRowCount()];
        
        for (int i = 0; i < datos.length; i++) {
            datos[i] = Integer.parseInt(tablaP.getValueAt(i, 1).toString());
            
        }
          
    
    
       XYSeries series = new XYSeries("Disco Duro");
       int superior=0;
       int inferior=0;
        int banderasup=0;
        int posicion=Integer.parseInt(txtInicio.getText());
        int posicionBandera=0;
        int ultimo=0;
        int contador=0;
        
        while ((posicion > 0) && (posicion < 201)) {
            series.add(contador, posicion);
            estadisticas[contador] = posicion;
            
            ultimo=posicion;
            contador++;
            //CALCULAR SUPERIOR MAS CERCANO
            for (int sup = posicion; sup < 201; sup++) {
                for (int d = 0; d < datos.length; d++) {
                    if (datos[d] == sup) {
                        superior = datos[d];
                        sup = 201;
                        break;
                    }
                }
            }//Fin de superior mas cercano

            //CALCULAR INFERIOR MAS CERCANO
            for (int inf = posicion; inf > 0; inf--) {
                for (int d = 0; d < datos.length; d++) {
                    if (datos[d] == inf) {
                        inferior = datos[d];
                        inf = 0;
                        break;
                    }
                }

            }//FIN DE CALCULAR INFERIOR MAS CERCANO

            int restaSup = superior - posicion;
            int restaInf = posicion - inferior;

            if ((superior == 0) && (inferior == 0)) {

                posicion = 0;

            } else {

                if (superior == 0) {
                    posicion = inferior;
                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i] == inferior) {
                            datos[i] = 0;
                            break;
                        }
                    }

                } else {
                    if (inferior == 0) {
                        posicion = superior;
                        for (int i = 0; i < datos.length; i++) {
                            if (datos[i] == superior) {
                                datos[i] = 0;
                                break;
                            }
                        }
                    } else {
                        if (restaSup < restaInf) {
                            posicion = superior;

                            for (int i = 0; i < datos.length; i++) {
                                if (datos[i] == superior) {
                                    datos[i] = 0;
                                    break;
                                }

                            }

                        } else {
                            posicion = inferior;

                            for (int i = 0; i < datos.length; i++) {
                                if (datos[i] == inferior) {
                                    datos[i] = 0;
                                    break;
                                }

                            }

                        }
                    }
                }
            }

            superior = 0;
            inferior = 0;
            
            if(posicion!=0){
            reporte = reporte + posicion + ",";
            }

        }
      XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Peticiones", // Título
                "Movimientos", // Etiqueta Coordenada X
                "Pistas", // Etiqueta Coordenada Y
                dataset, // Datos
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );

        // Mostramos la grafica en pantalla
        ChartFrame frame = new ChartFrame("Shortest Seek Time First", chart);
        frame.pack();
        frame.setVisible(true);
        
        int totalPistas=0;
        int pistas=0;
        int promedio=0;
        int varianza=0;
       for(int i=0; i<estadisticas.length-1;i++){
           totalPistas=totalPistas+Math.abs(estadisticas[i+1]-estadisticas[i]);
           tablaReporte.setValueAt(Math.abs(estadisticas[i+1]-estadisticas[i]), i+1, 3);
       }
       promedio= (totalPistas)/(estadisticas.length-1);
      for(int i=0; i<estadisticas.length-1;i++){
           pistas=Math.abs(estadisticas[i+1]-estadisticas[i]);
           varianza=varianza+(pistas-promedio)*(pistas-promedio);
       }
      varianza=varianza/(estadisticas.length-1);
      
       tsstf.setText(String.valueOf(totalPistas));
      psstf.setText(String.valueOf(promedio));
      vsstf.setText(String.valueOf(varianza));
      
      for(int i=0; i<estadisticas.length;i++){
          tablaReporte.setValueAt(String.valueOf(estadisticas[i]), i, 2);
      }
    }
    //------------------FIN METODO DE SSTF-----------------//
    //------------------METODO DE SCAN---------------------//
    public void scan(){
         int ultimo = 0;
        int inicio = Integer.parseInt(txtInicio.getText());
         int estadisticas[]= new int [tablaP.getRowCount()+1];
       estadisticas[0]=inicio;
        String reporte = "Orden de numeros: ";

        //Recoleccion de datos
        String datos[][] = new String[tablaP.getRowCount()][2];
        for (int i = 0; i < tablaP.getRowCount(); i++) {
            datos[i][0] = tablaP.getValueAt(i, 0).toString();
            datos[i][1] = tablaP.getValueAt(i, 1).toString();
        }

        XYSeries series = new XYSeries("Disco Duro");

        // Introduccion de datos
        //Orden de los datos
        String datosO[] = new String[tablaP.getRowCount()];
        int c = 1;
        series.add(0, inicio);
        for (int i = inicio; i>0; i--) {
            for (int k = 0; k < tablaP.getRowCount(); k++) {
                int n = Integer.parseInt(datos[k][1]);
                if (n == i) {
                    reporte = reporte + n + ",";
                    series.add(c, n);
                    estadisticas[c]=n;
                    ultimo = n;
                    c++;

                }
            }

        }//Fin de primera vuelta

        //Segunda vuelta
        for (int i = inicio; i <201; i++) {
            for (int k = 0; k < tablaP.getRowCount(); k++) {
                int n = Integer.parseInt(datos[k][1]);
                if (n == i) {
                    series.add(c, n);
                    estadisticas[c]=n;
                    reporte = reporte + n + ",";
                    ultimo = n;
                    c++;

                }
            }

        }//FIN DE ORDENAR LOS DATOS

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
            "Peticiones", // Título
            "Movimientos", // Etiqueta Coordenada X
            "Pistas", // Etiqueta Coordenada Y
            dataset, // Datos
            PlotOrientation.VERTICAL,
            true,
            false,
            false
        );

        // Mostramos la grafica en pantalla
        ChartFrame frame = new ChartFrame("SCAN", chart);
        frame.pack();
        frame.setVisible(true);

          int totalPistas=0;
        int pistas=0;
        int promedio=0;
        int varianza=0;
       for(int i=0; i<estadisticas.length-1;i++){
           totalPistas=totalPistas+Math.abs(estadisticas[i+1]-estadisticas[i]);
            tablaReporte.setValueAt(Math.abs(estadisticas[i+1]-estadisticas[i]), i+1, 5);
           
       }
       promedio= (totalPistas)/(estadisticas.length-1);
      for(int i=0; i<estadisticas.length-1;i++){
           pistas=Math.abs(estadisticas[i+1]-estadisticas[i]);
           varianza=varianza+(pistas-promedio)*(pistas-promedio);
       }
      varianza=varianza/(estadisticas.length-1);
      
       tscan.setText(String.valueOf(totalPistas));
      pscan.setText(String.valueOf(promedio));
      vscan.setText(String.valueOf(varianza));
      
      for(int i=0; i<estadisticas.length;i++){
          tablaReporte.setValueAt(String.valueOf(estadisticas[i]), i, 4);
      }
    }
    //------------------FIN METODO DE SCAN-----------------//
    //------------------METODO DE CSCAN---------------------//
    public void cscan(){
        int ultimo = 0;
        int inicio = Integer.parseInt(txtInicio.getText());
        String reporte = "Orden de numeros: ";
       int estadisticas[]= new int [tablaP.getRowCount()+1];
       estadisticas[0]=inicio;
        //Recoleccion de datos
        String datos[][] = new String[tablaP.getRowCount()][2];
        for (int i = 0; i < tablaP.getRowCount(); i++) {
            datos[i][0] = tablaP.getValueAt(i, 0).toString();
            datos[i][1] = tablaP.getValueAt(i, 1).toString();
        }
        
        
        XYSeries series = new XYSeries("Disco Duro");

        // Introduccion de datos
        //Orden de los datos
        String datosO[] = new String[tablaP.getRowCount()];
        int c = 1;
        series.add(0, inicio);
       
        for (int i = inicio; i <= 200; i++) {
            for (int k = 0; k < tablaP.getRowCount(); k++) {
                int n = Integer.parseInt(datos[k][1]);
                if (n == i) {
                    reporte = reporte + n + ",";
                    series.add(c, n);
                     estadisticas[c]=n;
                    ultimo = n;
                    c++;

                }
            }

        }//Fin de primera vuelta

        
        //Segunda vuelta
        for (int i = 1; i < inicio; i++) {
            for (int k = 0; k < tablaP.getRowCount(); k++) {
                int n = Integer.parseInt(datos[k][1]);
                if (n == i) {
                    series.add(c, n);
                    reporte = reporte + n + ",";
                     estadisticas[c]=n;
                    ultimo = n;
                    c++;

                }
            }

        }//FIN DE ORDENAR LOS DATOS

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Peticiones", // Título
                "Movimientos", // Etiqueta Coordenada X
                "Pistas", // Etiqueta Coordenada Y
                dataset, // Datos
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );

        // Mostramos la grafica en pantalla
        ChartFrame frame = new ChartFrame("C-SCAN", chart);
        frame.pack();
        frame.setVisible(true);
       
        int totalPistas=0;
        int pistas=0;
        int promedio=0;
        int varianza=0;
       for(int i=0; i<estadisticas.length-1;i++){
           totalPistas=totalPistas+Math.abs(estadisticas[i+1]-estadisticas[i]);
           tablaReporte.setValueAt(Math.abs(estadisticas[i+1]-estadisticas[i]), i+1, 7);
           
       }
       promedio= (totalPistas)/(estadisticas.length-1);
      for(int i=0; i<estadisticas.length-1;i++){
           pistas=Math.abs(estadisticas[i+1]-estadisticas[i]);
           varianza=varianza+(pistas-promedio)*(pistas-promedio);
       }
      varianza=varianza/(estadisticas.length-1);
      
        tcscan.setText(String.valueOf(totalPistas));
      pcscan.setText(String.valueOf(promedio));
      vcscan.setText(String.valueOf(varianza));
      
      for(int i=0; i<estadisticas.length;i++){
          tablaReporte.setValueAt(String.valueOf(estadisticas[i]), i, 6);
      }
    }
    //------------------FIN METODO DE CSCAN-----------------//

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaP = new javax.swing.JTable();
        btnEjecutar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        txtInicio = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtPeticion = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        btnAleatorio = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaReporte = new javax.swing.JTable();
        tfifo = new javax.swing.JTextField();
        tsstf = new javax.swing.JTextField();
        tscan = new javax.swing.JTextField();
        tcscan = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        pfifo = new javax.swing.JTextField();
        psstf = new javax.swing.JTextField();
        pscan = new javax.swing.JTextField();
        pcscan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        vfifo = new javax.swing.JTextField();
        vsstf = new javax.swing.JTextField();
        vscan = new javax.swing.JTextField();
        vcscan = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tablaP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "id", "Peticion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaP);

        btnEjecutar.setText("Ejecutar");
        btnEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEjecutarActionPerformed(evt);
            }
        });

        jLabel3.setText("Inicio");

        jButton2.setText("Aleatorio");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("Vaciar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Numero de Pista (1-200)");

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnAleatorio.setText("Aleatorio");
        btnAleatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAleatorioActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setText("Reporte de comparacion de algoritmos");

        tablaReporte.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "FIFO", "Orden", "SSTF", "Orden", "SCAN", "Orden", "C-SCAN", "Orden"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaReporte);

        jLabel5.setText("Total:");

        jLabel6.setText("Promedio");

        jLabel7.setText("Varianza");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnAgregar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnAleatorio))
                                    .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(txtPeticion)))
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton2)))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(77, 77, 77)
                                        .addComponent(jLabel3))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jButton1)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(btnEjecutar))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(pfifo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(psstf, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(pscan, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(pcscan, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tfifo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(tsstf, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(tscan, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(tcscan, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(vfifo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(vsstf, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(vscan, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(vcscan, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(193, 193, 193)
                        .addComponent(jLabel4)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPeticion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnAgregar)
                                    .addComponent(btnAleatorio))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addGap(4, 4, 4)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEjecutar)
                            .addComponent(jButton1)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfifo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tsstf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tscan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tcscan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pfifo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(psstf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pscan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pcscan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(vfifo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vsstf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vscan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vcscan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEjecutarActionPerformed
          //Creacion de filas en la tabla de reporte
            String a[] = new String[8];
            a[0]="";
            a[1]="";
            a[2]="";
            a[3]="";
            a[4]="";
            a[5]="";
            a[6]="";
            a[7]="";
        for (int i = 0; i < tablaP.getRowCount()+1; i++) {    
            modeloReporte.addRow(a);
        }
        
        fifo();
        sstf();
        scan();
        cscan();
        
        

    }//GEN-LAST:event_btnEjecutarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Random tamaño = new Random();
        txtInicio.setText(String.valueOf(tamaño.nextInt(200)));
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        txtInicio.setText("");
        txtInicio.setEditable(true);
        
         tfifo.setText("");
         pfifo.setText("");
         vfifo.setText("");
         
        
         
          tsstf.setText("");
         psstf.setText("");
         vsstf.setText("");
         
          tscan.setText("");
         pscan.setText("");
         vscan.setText("");
         
          tcscan.setText("");
         pcscan.setText("");
         vcscan.setText("");
         
       
        try{
            while(true){
                modeloP.removeRow(0);
            }
        } catch (Exception e) {

        }
        try{
            while(true){
                modeloReporte.removeRow(0);
            }
        } catch (Exception e) {

        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        if(tablaP.getRowCount()>50  ){
            JOptionPane.showMessageDialog(null,"Solo se permiten 50 direcciones");
        }else{
        String []fila= new String[2];
        String nombre= "D"+contadorP;
        fila[0]= nombre;
        fila[1]= txtPeticion.getText();

        modeloP.addRow(fila);
        contadorP++;
        txtPeticion.setText("");
        txtPeticion.requestFocus();
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnAleatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAleatorioActionPerformed
        // TODO add your handling code here:
        int k= Integer.parseInt(JOptionPane.showInputDialog(null,"Numero de pistas que desea crear (Limite 50)"));
        if((k>50)||(tablaP.getRowCount()>50 )){
            JOptionPane.showMessageDialog(null,"Solo se permiten 50 direcciones en la tabla");
        }else{
        String []datos=new String[2];
        Random tamaño = new Random();
        for(int i=0;i<k;i++){
            datos[0] = "R"+String.valueOf(i+100);
            datos[1] = String.valueOf(1+tamaño.nextInt(200));

            modeloP.addRow(datos);
        }
        }
    }//GEN-LAST:event_btnAleatorioActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Comparacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Comparacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Comparacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Comparacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Comparacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAleatorio;
    private javax.swing.JButton btnEjecutar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField pcscan;
    private javax.swing.JTextField pfifo;
    private javax.swing.JTextField pscan;
    private javax.swing.JTextField psstf;
    private javax.swing.JTable tablaP;
    private javax.swing.JTable tablaReporte;
    private javax.swing.JTextField tcscan;
    private javax.swing.JTextField tfifo;
    private javax.swing.JTextField tscan;
    private javax.swing.JTextField tsstf;
    private javax.swing.JTextField txtInicio;
    private javax.swing.JTextField txtPeticion;
    private javax.swing.JTextField vcscan;
    private javax.swing.JTextField vfifo;
    private javax.swing.JTextField vscan;
    private javax.swing.JTextField vsstf;
    // End of variables declaration//GEN-END:variables
}
