package pl.sages.jext.lambdastreams.step1.lambda;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Krok 1 na deser: prawdziwy przycisk ze Swinga.
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

      /*  jButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Przyciśnięty");
            }
        });*/
        jButton.addActionListener( _ -> System.out.println("Przyciśnięty"));
       // jButton.addActionListener(e -> jButton.setText("" + new Random().nextInt(1, 11)));
        frame.setVisible(true);

    }
}
