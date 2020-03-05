/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activitat5;

import activitat5.entity.Moviment;
import activitat5.entity.MovimentId;
import activitat5.entity.Partida;
import activitat5Util.HibernateUtil;
import java.awt.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JDialog;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Alumne
 */
public class NovaPartida extends javax.swing.JFrame {
    //variables
    boolean jugaX = true;
    boolean jugaO = false;
    int filaOrigen = -1;
    int columnaOrigen = -1;
    String guanyador = "";
    int countMov = 0;
    Partida partida;
    Set moviments = new HashSet<Moviment>();
    //ArrayList<Moviment> moviments = new ArrayList<>();
    String taulellMoviments = new String();
    Session session;
    SessionFactory sessionFactory;

    /**
     * Creates new form NovaPartida
     */
    public NovaPartida() {        
        initComponents();
        jugador.setText("X");
        sessionFactory = HibernateUtil.getSessionFactory();
        partida = new Partida(new Date(),guanyador, moviments);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSortir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        taulell = new javax.swing.JTable();
        error = new javax.swing.JLabel();
        jugador = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnSortir.setText("Sortir");
        btnSortir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortirActionPerformed(evt);
            }
        });

        taulell.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"X", null, "X", null, "X", null, "X", null},
                {null, "X", null, "X", null, "X", null, "X"},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {"O", null, "O", null, "O", null, "O", null},
                {null, "O", null, "O", null, "O", null, "O"}
            },
            new String [] {
                "1", "2", "3", "4", "5", "6", "7", "8"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        taulell.setColumnSelectionAllowed(true);
        taulell.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                taulellMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(taulell);
        taulell.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        error.setToolTipText("");

        jugador.setText(jugador.getText());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(error)
                    .addComponent(jugador))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSortir)
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(btnSortir))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(error)
                        .addGap(9, 9, 9)
                        .addComponent(jugador)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Event Click a la taula
    private void taulellMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taulellMouseClicked
        int fila = obtenirFilaClicada();
        int columna = obtenirColumnaClicada();
        error.setText("");
        
        
        if(jugaX){
            jugador.setText("X");
        }else{
            jugador.setText("O");
        }

        if (noHiHaOrigen()) {
            if (jugaX && EsX(fila,columna)){
                actualitzaNouOrigen(fila,columna);
            } else if (jugaO && EsO(fila,columna)){
                actualitzaNouOrigen(fila,columna);
            }else{
		mostraError();
            }
        }else{
            if(movimentVàlid(fila,columna)){//si diagonal cap avall per X o cap a dalt per O
                if(esBuit(fila,columna) || OcupatContrari(fila,columna)){
                    mou(fila,columna);
                }
            }else{
                if(OcupatPropi(fila,columna)){
                    actualitzaNouOrigen(fila,columna);
                }else{
                    mostraErrorMoviment();
                }
            }
        }         
    }//GEN-LAST:event_taulellMouseClicked

    private void btnSortirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSortirActionPerformed
        //tancar programa
        System.exit(0);
    }//GEN-LAST:event_btnSortirActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSortir;
    private javax.swing.JLabel error;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jugador;
    private javax.swing.JTable taulell;
    // End of variables declaration//GEN-END:variables

    
    public int obtenirFilaClicada(){
        return taulell.getSelectedRow();
    }

    public int obtenirColumnaClicada(){
        return taulell.getSelectedColumn();
    }

    public boolean noHiHaOrigen(){
        if (filaOrigen == -1){
            return true;
        }else{
            return false;
        }
    }

    public boolean EsX(int fila, int columna){
        return (!esBuit(fila, columna) && taulell.getValueAt(fila, columna).equals("X"));
    }

    public boolean EsO(int fila, int columna){
        return (!esBuit(fila, columna) && taulell.getValueAt(fila, columna).equals("O"));
    }

    public void actualitzaNouOrigen(int fila, int columna){
        filaOrigen = fila;
        columnaOrigen = columna;
    }

    public void mostraError(){
        error.setText("Error al clicar casella");
    }

    public boolean movimentVàlid(int fila, int columna){
        if (jugaX){
            if((fila == filaOrigen + 1) && (columna == columnaOrigen + 1 || columna == columnaOrigen - 1)){
                return true;
            }else{
                return false;
            }
        }else{
            if((fila == filaOrigen - 1) && (columna == columnaOrigen + 1 || columna == columnaOrigen - 1)){
                return true;
            }else{
                return false;
            }
        }
    }

    public boolean esBuit(int fila, int columna){
        return (taulell.getValueAt(fila, columna) == (null));
    }

    public boolean OcupatContrari(int fila, int columna){
        if(!esBuit(fila, columna)){
            if(jugaX){
                return (taulell.getValueAt(fila, columna).equals("O"));
            }else{
                return (taulell.getValueAt(fila, columna).equals("X"));
            }
        }
        return false;
    }

    public void mou(int fila, int columna){
        countMov++;
        //borra fitxa
        taulell.setValueAt(null, filaOrigen, columnaOrigen);

        if (jugaX){
            //mou fitxa
            taulell.setValueAt("X", fila, columna);
            //canvi de jugador
            jugaX = false;
            jugaO = true;
        }else{
            //mou fitxa
            taulell.setValueAt("O", fila, columna);
            //canvi de jugador
            jugaX = true;
            jugaO = false;
        }

        //inicialitzar origen
        filaOrigen = -1;
        columnaOrigen = -1;
        //guardar moviments en bd
        desarEnBD();
        if (acaba(fila)){
            System.exit(0);            
        }
    }

    public boolean OcupatPropi(int fila, int columna){
        if(!esBuit(fila, columna)){
            if(jugaX){
                return (taulell.getValueAt(fila, columna).equals("X"));
            }else{
                return (taulell.getValueAt(fila, columna).equals("O"));
            }
        }
        return false;
    }

    public void mostraErrorMoviment(){
        error.setText("Error al moure");
    }

    public boolean acaba(int fila){
        //Alguna fitxa X arriba al final del tauler. Guanya X || X elimina totes les fitxes O. Guanya X.
        if(jugaX && fila == 7 || eliminaContrincant("O")){
            guanyador = "X";
            return true;
        }
        //Alguna fitxa O arriba a dalt del tauler. Guanya O || O elimina totes les fitxes X. Guanya O
        if(jugaO && fila == 0 || eliminaContrincant("X")){
            guanyador = "O";
            return true;
        }     
        
        return false;
    }
    
    public boolean eliminaContrincant(String lletra){
        int countLletra = 0;        
        
        for (int i = 0; i < taulell.getRowCount(); i++) {
           for (int j = 0; j < taulell.getColumnCount(); j++) {
               if(!esBuit(i,j) && taulell.getValueAt(i, j) == lletra){
                   countLletra++;
               }
           }
        }
        if(countLletra == 0){
            guanyador = lletra;
            return true;
        }else{
            return false;
        }
    }
    
    public void desarEnBD(){
        taulellMoviments = new String();
        for (int i = 0; i < taulell.getRowCount(); i++) {
           for (int j = 0; j < taulell.getColumnCount(); j++) {
               taulellMoviments += (taulell.getValueAt(i,j)==null)?" ":taulell.getValueAt(i,j);
           }
        }
        
        session = sessionFactory.openSession();
        
        if(partida.getPartidaId() == null){            
            session.beginTransaction();
            session.save(partida);
            session.getTransaction().commit();
            }
        
        MovimentId mid = new MovimentId(partida.getPartidaId(),countMov);
        Moviment m = new Moviment(mid,partida,taulellMoviments);
        moviments.add(m);
        partida.setMoviments(moviments); 
        
         session.beginTransaction();
         session.save(m);
         session.getTransaction().commit();
        
        //tancar sessió
         session.close();
        
    }
}