package com.ltoscano.ia.flappybird.game.gui;

import com.ltoscano.ia.flappybird.game.GeneticLearning;
import com.ltoscano.ia.flappybird.game.NeuralLearning;
import com.ltoscano.ia.flappybird.game.config.GameConfig;
import com.ltoscano.ia.flappybird.game.config.PipeConfig;
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
public class FlappyBirdNeuralConfigWindow extends javax.swing.JFrame {

    /**
     * Creates new form FlappyBirdTestWindow
     */
    public FlappyBirdNeuralConfigWindow() 
    {
        initComponents();
        setLocationRelativeTo(null);
        
        spinHiddenLayerNeuronCount.setValue(NeuralLearning.NEURAL_HIDDEN_LAYER_NEURON_COUNT);
        
        switch(NeuralLearning.NEURAL_ACTIVATION_FUNCTION)
        {
            case Hyperbolic:
                comboNeuralActivationFunction.setSelectedIndex(0);
                break;
            case Sigmoid:
                comboNeuralActivationFunction.setSelectedIndex(1);
                break;
        }
        
        spinNeuralActivationLimiar.setValue(NeuralLearning.NEURAL_ACTIVATION_FUNCTION_LIMIAR);
        spinNeuralBias.setValue(NeuralLearning.NEURAL_BIAS_VALUE);
        
        spinNeuralLearnRate.setValue(NeuralLearning.NEURAL_LEARN_RATE);
        spinNeuralMaxIteration.setValue(NeuralLearning.NEURAL_MAX_ITERATION);
        
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
        jLabel12 = new javax.swing.JLabel();
        spinNeuralLearnRate = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        spinNeuralMaxIteration = new javax.swing.JSpinner();
        btnTrain = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        spinScoreLimit = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        spinPipeSpaceSize = new javax.swing.JSpinner();
        btnReset = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Neural Config");
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

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Taxa de aprendizado:");

        spinNeuralLearnRate.setModel(new javax.swing.SpinnerNumberModel(0.7d, 0.1d, 1.0d, 0.1d));
        spinNeuralLearnRate.setValue(0.0);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Máximo de iterações no treinamento:");

        spinNeuralMaxIteration.setModel(new javax.swing.SpinnerNumberModel(100000, 1, null, 1));
        spinNeuralMaxIteration.setValue(0.0);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(spinNeuralActivationLimiar, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                .addComponent(comboNeuralActivationFunction, 0, 1, Short.MAX_VALUE))
                            .addComponent(spinHiddenLayerNeuronCount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 100, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel10))
                                .addGap(27, 27, 27)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(spinNeuralBias, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spinNeuralLearnRate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(spinNeuralMaxIteration, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                    .addComponent(jLabel6)
                    .addComponent(spinNeuralActivationLimiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinNeuralBias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinNeuralLearnRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(spinNeuralMaxIteration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnTrain.setText("Treinar");
        btnTrain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrainActionPerformed(evt);
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
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spinScoreLimit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinPipeSpaceSize, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTrain, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTrain, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTrainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrainActionPerformed
        try 
        {
            spinHiddenLayerNeuronCount.commitEdit();
            spinNeuralActivationLimiar.commitEdit();
            spinNeuralBias.commitEdit();
            spinScoreLimit.commitEdit();
            spinPipeSpaceSize.commitEdit();
            
            spinNeuralLearnRate.commitEdit();
            spinNeuralMaxIteration.commitEdit();
        } 
        catch (ParseException ex) { }
        
        NeuralLearning.NEURAL_HIDDEN_LAYER_NEURON_COUNT = ((Number)spinHiddenLayerNeuronCount.getValue()).intValue();
        
        switch(comboNeuralActivationFunction.getSelectedIndex())
        {
            case 0:
                NeuralLearning.NEURAL_ACTIVATION_FUNCTION = NeuralLearning.NeuronActivationFunction.Hyperbolic;
                break;
            case 1:
                NeuralLearning.NEURAL_ACTIVATION_FUNCTION = NeuralLearning.NeuronActivationFunction.Sigmoid;
                break;
        }
        
        NeuralLearning.NEURAL_ACTIVATION_FUNCTION_LIMIAR = ((Number)spinNeuralActivationLimiar.getValue()).doubleValue();
        NeuralLearning.NEURAL_BIAS_VALUE = ((Number)spinNeuralBias.getValue()).doubleValue();
        
        NeuralLearning.NEURAL_LEARN_RATE = ((Number)spinNeuralLearnRate.getValue()).doubleValue();
        NeuralLearning.NEURAL_MAX_ITERATION = ((Number)spinNeuralMaxIteration.getValue()).intValue();
        
        GameConfig.SCORE_LIMIT = ((Number)spinScoreLimit.getValue()).intValue();
        PipeConfig.PIPE_SPACE_SIZE = ((Number)spinPipeSpaceSize.getValue()).intValue();
        
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                try 
                {
                    NeuralLearning neuralLearning = new NeuralLearning();
                    neuralLearning.newInstance();
                } 
                catch (IOException ex) 
                {
                    Logger.getLogger(FlappyBirdGeneticConfigWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        this.dispose();
    }//GEN-LAST:event_btnTrainActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        spinHiddenLayerNeuronCount.setValue(3);
        comboNeuralActivationFunction.setSelectedIndex(0);
        spinNeuralActivationLimiar.setValue(0.0);
        spinNeuralBias.setValue(0.0);
        
        spinScoreLimit.setValue(100);
        spinPipeSpaceSize.setValue(70);
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
                    new FlappyBirdNeuralConfigWindow().setVisible(true);
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
    private javax.swing.JButton btnTrain;
    private javax.swing.JComboBox<String> comboNeuralActivationFunction;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSpinner spinHiddenLayerNeuronCount;
    private javax.swing.JSpinner spinNeuralActivationLimiar;
    private javax.swing.JSpinner spinNeuralBias;
    private javax.swing.JSpinner spinNeuralLearnRate;
    private javax.swing.JSpinner spinNeuralMaxIteration;
    private javax.swing.JSpinner spinPipeSpaceSize;
    private javax.swing.JSpinner spinScoreLimit;
    // End of variables declaration//GEN-END:variables
}
