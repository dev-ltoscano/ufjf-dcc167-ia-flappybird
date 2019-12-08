package com.ltoscano.ia.flappybird.game.gui;

import com.ltoscano.ia.flappybird.game.GeneticLearning;
import com.ltoscano.ia.flappybird.game.config.GameConfig;
import com.ltoscano.ia.flappybird.game.config.GeneticConfig;
import com.ltoscano.ia.flappybird.game.config.PipeConfig;
import com.ltoscano.ia.flappybird.game.config.AgentConfig;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author ltosc
 */
public class FlappyBirdGeneticConfigWindow extends javax.swing.JFrame {

    /**
     * Creates new form FlappyBirdTestWindow
     */
    public FlappyBirdGeneticConfigWindow() 
    {
        initComponents();
        setLocationRelativeTo(null);
        
        spinPopulationSize.setValue(GeneticConfig.POPULATION_SIZE);
        spinCrossoverRate.setValue(GeneticConfig.CROSSOVER_RATE * 100);
        spinMutationRate.setValue(GeneticConfig.MUTATION_RATE * 100);
        spinElitismRate.setValue(GeneticConfig.ELITISM_RATE * 100);
        spinMatchCount.setValue(GeneticConfig.GENERATION_MATCH_COUNT);
        
        spinHiddenLayerNeuronCount.setValue(AgentConfig.NEURAL_HIDDEN_LAYER_NEURON_COUNT);
        
        switch(AgentConfig.NEURAL_ACTIVATION_FUNCTION)
        {
            case Hyperbolic:
                comboNeuralActivationFunction.setSelectedIndex(0);
                break;
            case Sigmoid:
                comboNeuralActivationFunction.setSelectedIndex(1);
                break;
        }
        
        spinNeuralActivationLimiar.setValue(AgentConfig.NEURAL_ACTIVATION_FUNCTION_LIMIAR);
        spinNeuralBias.setValue(AgentConfig.NEURAL_BIAS_VALUE);
        
        spinScoreLimit.setValue(GameConfig.SCORE_LIMIT);
        spinPipeSpaceSize.setValue(PipeConfig.PIPE_SPACE_SIZE);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        spinHiddenLayerNeuronCount = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        comboNeuralActivationFunction = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        spinNeuralActivationLimiar = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        spinNeuralBias = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        spinElitismRate = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        spinPopulationSize = new javax.swing.JSpinner();
        spinMutationRate = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        spinCrossoverRate = new javax.swing.JSpinner();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        spinMatchCount = new javax.swing.JSpinner();
        btnStart = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        spinScoreLimit = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        spinPipeSpaceSize = new javax.swing.JSpinner();
        btnReset = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Genetic Config");
        setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Rede Neural", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(50, 50, 50))); // NOI18N

        spinHiddenLayerNeuronCount.setModel(new javax.swing.SpinnerNumberModel(3, 1, null, 1));
        spinHiddenLayerNeuronCount.setValue(2.0);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Neurônios da camada escondida:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Função de ativação dos neurônios:");

        comboNeuralActivationFunction.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        comboNeuralActivationFunction.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hyperbolic", "Sigmoid" }));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Limiar de ativação da rede:");

        spinNeuralActivationLimiar.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 0.1d));
        spinNeuralActivationLimiar.setValue(0.0);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Valor do Bias:");

        spinNeuralBias.setModel(new javax.swing.SpinnerNumberModel(0, -1, 1, 1));
        spinNeuralBias.setValue(0.0);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spinNeuralActivationLimiar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboNeuralActivationFunction, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(spinHiddenLayerNeuronCount, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(spinNeuralBias, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(spinHiddenLayerNeuronCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(comboNeuralActivationFunction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinNeuralActivationLimiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinNeuralBias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Algoritmo Genético", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(50, 50, 50))); // NOI18N

        spinElitismRate.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        spinElitismRate.setModel(new javax.swing.SpinnerNumberModel(10.0d, 1.0d, 100.0d, 0.1d));
        spinElitismRate.setValue(10.0);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Taxa de elitismo (%):");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Tamanho da população:");

        spinPopulationSize.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        spinPopulationSize.setModel(new javax.swing.SpinnerNumberModel(10, 2, null, 1));
        spinPopulationSize.setValue(1000.0);

        spinMutationRate.setModel(new javax.swing.SpinnerNumberModel(0.25d, 0.0d, 100.0d, 0.1d));
        spinMutationRate.setValue(0.5);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Taxa de mutação (%):");

        spinCrossoverRate.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        spinCrossoverRate.setModel(new javax.swing.SpinnerNumberModel(85.0d, 0.0d, 100.0d, 0.1d));
        spinCrossoverRate.setValue(85.0);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Taxa de crossover (%):");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Quantidade de partidas por geração:");

        spinMatchCount.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        spinMatchCount.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        spinMatchCount.setValue(10.0);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel7)
                        .addComponent(jLabel13)
                        .addComponent(jLabel9))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel12))))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spinCrossoverRate, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinPopulationSize, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinMutationRate, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinElitismRate, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinMatchCount, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(spinPopulationSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(spinCrossoverRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinMutationRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(spinElitismRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(spinMatchCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnStart.setText("Iniciar");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Game", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(50, 50, 50))); // NOI18N

        spinScoreLimit.setModel(new javax.swing.SpinnerNumberModel(100, -1, null, 1));
        spinScoreLimit.setValue(100.0);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Limite de pontuação:");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("Tamanho da abertura dos canos:");

        spinPipeSpaceSize.setModel(new javax.swing.SpinnerNumberModel(70, 0, 200, 1));
        spinPipeSpaceSize.setValue(65.0);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(spinScoreLimit, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinPipeSpaceSize, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(spinScoreLimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinPipeSpaceSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnReset.setText("Resetar");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        try 
        {
            spinPopulationSize.commitEdit();
            spinCrossoverRate.commitEdit();
            spinMutationRate.commitEdit();
            spinElitismRate.commitEdit();
            spinMatchCount.commitEdit();
            spinHiddenLayerNeuronCount.commitEdit();
            spinNeuralActivationLimiar.commitEdit();
            spinNeuralBias.commitEdit();
            spinScoreLimit.commitEdit();
            spinPipeSpaceSize.commitEdit();
        } 
        catch (ParseException ex) { }
        
        GeneticConfig.POPULATION_SIZE = ((Number)spinPopulationSize.getValue()).intValue();
        GeneticConfig.CROSSOVER_RATE = (((Number)spinCrossoverRate.getValue()).doubleValue() / 100);
        GeneticConfig.MUTATION_RATE = (((Number)spinMutationRate.getValue()).doubleValue() / 100);
        GeneticConfig.ELITISM_RATE = (((Number)spinElitismRate.getValue()).doubleValue() / 100);
        GeneticConfig.ELITISM_SIZE = 
                ((GeneticConfig.POPULATION_SIZE * GeneticConfig.ELITISM_RATE) >= 1) ? 
                ((int)(GeneticConfig.POPULATION_SIZE * GeneticConfig.ELITISM_RATE)) : 1;
        
        GeneticConfig.GENERATION_MATCH_COUNT = ((Number)spinMatchCount.getValue()).intValue();
        
        AgentConfig.NEURAL_HIDDEN_LAYER_NEURON_COUNT = ((Number)spinHiddenLayerNeuronCount.getValue()).intValue();
        
        switch(comboNeuralActivationFunction.getSelectedIndex())
        {
            case 0:
                AgentConfig.NEURAL_ACTIVATION_FUNCTION = AgentConfig.NeuronActivationFunction.Hyperbolic;
                break;
            case 1:
                AgentConfig.NEURAL_ACTIVATION_FUNCTION = AgentConfig.NeuronActivationFunction.Sigmoid;
                break;
        }
        
        AgentConfig.NEURAL_ACTIVATION_FUNCTION_LIMIAR = ((Number)spinNeuralActivationLimiar.getValue()).doubleValue();
        AgentConfig.NEURAL_BIAS_VALUE = ((Number)spinNeuralBias.getValue()).doubleValue();
        
        GameConfig.SCORE_LIMIT = ((Number)spinScoreLimit.getValue()).intValue();
        PipeConfig.PIPE_SPACE_SIZE = ((Number)spinPipeSpaceSize.getValue()).intValue();
        
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                try 
                {
                    GeneticLearning geneticLearning = new GeneticLearning();
                    geneticLearning.newInstance();
                } 
                catch (IOException ex) 
                {
                    Logger.getLogger(FlappyBirdGeneticConfigWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        this.dispose();
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        spinPopulationSize.setValue(1000);
        spinCrossoverRate.setValue(85);
        spinMutationRate.setValue(0.5);
        spinElitismRate.setValue(10);
        
        spinHiddenLayerNeuronCount.setValue(2);
        comboNeuralActivationFunction.setSelectedIndex(0);
        spinNeuralActivationLimiar.setValue(0.0);
        spinNeuralBias.setValue(0.0);
        
        spinScoreLimit.setValue(100);
        spinPipeSpaceSize.setValue(65);
    }//GEN-LAST:event_btnResetActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        try 
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable()
            {
                public void run() 
                {
                    new FlappyBirdGeneticConfigWindow().setVisible(true);
                }
            });
        } 
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) 
        {
            Logger.getLogger(FlappyBirdGeneticConfigWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnStart;
    private javax.swing.JComboBox<String> comboNeuralActivationFunction;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSpinner spinCrossoverRate;
    private javax.swing.JSpinner spinElitismRate;
    private javax.swing.JSpinner spinHiddenLayerNeuronCount;
    private javax.swing.JSpinner spinMatchCount;
    private javax.swing.JSpinner spinMutationRate;
    private javax.swing.JSpinner spinNeuralActivationLimiar;
    private javax.swing.JSpinner spinNeuralBias;
    private javax.swing.JSpinner spinPipeSpaceSize;
    private javax.swing.JSpinner spinPopulationSize;
    private javax.swing.JSpinner spinScoreLimit;
    // End of variables declaration//GEN-END:variables
}
