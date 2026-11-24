package pl.sages.jext.lambdastreams.step1.lambda;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.util.Random;

/**
 * Krok 1 na deser: prawdziwy przycisk ze Swinga (Comarch 2025, OkienkaPrzyklad).
 * {@code ActionListener} ze Swinga to też interfejs funkcyjny: (ActionEvent) -> void.
 * Otwiera okienko, więc uruchamiaj tylko na komputerze z ekranem.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.lambdastreams.step1.lambda.SwingButton}
 */
public class SwingButton {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Okienko");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 100);
        JButton jButton = new JButton("Naciśnij");
        frame.add(jButton);
        jButton.addActionListener(e -> jButton.setText("" + new Random().nextInt(1, 11)));
        frame.setVisible(true);
    }
}
