import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class JadeSays extends JFrame implements ActionListener {

    JLabel title = new JLabel();
    JButton blue = new JButton();
    JButton red = new JButton();
    JButton green = new JButton();
    JButton pink = new JButton();
    JButton Startgame = new JButton();
    JButton Exitgame = new JButton();
    private Image backgroundImage;
    ArrayList<String> answerKey = new ArrayList<>();
    ArrayList<String> playerAnswer = new ArrayList<>();
    int rounds = 1;
    int numClick = 0;

    public JadeSays() {
        
        try {
            backgroundImage = ImageIO.read(new File("src\\images\\haynaku.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.setLayout(null);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 515, 535);
        this.add(layeredPane);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.setBounds(0, 0, 515, 535);
        backgroundPanel.setLayout(null);

      
        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

       
        title.setText("Play Jade Says");
        title.setForeground(Color.PINK);
        title.setBounds(60, 50, 400, 50); 
        title.setFont(new Font("Times New Roman", Font.BOLD, 30));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(null);
        titlePanel.setOpaque(false); 
        titlePanel.setBounds(0, 0, 515, 535);
        titlePanel.add(title);

        layeredPane.add(titlePanel, JLayeredPane.PALETTE_LAYER);

        
        Startgame.setBounds(100, 200, 125, 50);
        Startgame.setText("Start Game");
        Startgame.addActionListener(this);

        Exitgame.setBounds(290, 200, 125, 50);
        Exitgame.setText("Quit Game");
        Exitgame.addActionListener(this);

        blue.setBackground(Color.BLUE);
        blue.setBounds(0, 0, 250, 250);
        blue.addActionListener(this);

        red.setBackground(Color.RED);
        red.setBounds(0, 250, 250, 250);
        red.addActionListener(this);

        green.setBackground(Color.GREEN);
        green.setBounds(250, 0, 250, 250);
        green.addActionListener(this);

        pink.setBackground(Color.PINK);
        pink.setBounds(250, 250, 250, 250);
        pink.addActionListener(this);

        
        blue.setVisible(false);
        red.setVisible(false);
        green.setVisible(false);
        pink.setVisible(false);

    
        titlePanel.add(Startgame);
        titlePanel.add(Exitgame);
        titlePanel.add(blue);
        titlePanel.add(red);
        titlePanel.add(green);
        titlePanel.add(pink);

        
        this.setSize(515, 535);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setTitle("Jade Says");
    }

    public void MainPage() {
        title.setText("Play Jade Says");
        title.setVisible(true);
        Startgame.setVisible(true);
        Exitgame.setVisible(true);
    }

    public void Start(){

        this.add(blue);
        this.add(red);
        this.add(green);
        this.add(pink);
    

    }

    public void ColorSequence(){

        int randomizer = (int)(Math.random() * (4-1+1)) + 1;

        switch (randomizer) {
            case 1 -> answerKey.add("Blue");
            case 2 -> answerKey.add("Red");
            case 3 -> answerKey.add("Green");
            case 4 -> answerKey.add("Pink");    
               
        }

    }
    
    public void flashButton(JButton buttonToFlash) {
        if (buttonToFlash != null) {
            
            final Color originalColor = buttonToFlash.getBackground(); 
    
           
            buttonToFlash.setBackground(Color.WHITE);
    
            
            new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buttonToFlash.setBackground(originalColor); 
                    ((Timer) e.getSource()).stop();  
                }
            }).start();
        }
    }


    public void flashColors() {
        Timer timer = new Timer(1000, new ActionListener() {
            int index = 0;
    
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index < answerKey.size()) {
                    String color = answerKey.get(index);
                    JButton buttonToFlash = getButtonForColor(color); 
                    flashButton(buttonToFlash);
                    index++;
                } else {
                    ((Timer) e.getSource()).stop(); 
                }
            }
        });
        
        timer.setInitialDelay(500);  
        timer.start();
    }
    
    private JButton getButtonForColor(String color) {
        switch (color) {
            case "Blue":
                return blue;
            case "Red":
                return red;
            case "Green":
                return green;
            case "Pink":
                return pink;
            default:
                return null; 
        }
    }

    public void getAnswer() {
        boolean isCorrect = true;
    
       
        for (int i = 0; i < playerAnswer.size(); i++) {
            if (!playerAnswer.get(i).equalsIgnoreCase(answerKey.get(i))) {
                isCorrect = false;
                break;
            }
        }
    
        if (isCorrect) {
            if (playerAnswer.size() == answerKey.size()) {
                
                numClick = 0;
                rounds++;
               
                playerAnswer.clear();
    
                ColorSequence();
                System.out.println("Next Round Colors: " + answerKey);
    
                flashColors();
            }
        } else {
    
            numClick = 0;
            rounds = 1;
            playerAnswer.clear();
            answerKey.clear();
            blue.setVisible(false);
            red.setVisible(false);
            green.setVisible(false);
            pink.setVisible(false);
            MainPage();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource() == Startgame){

            Startgame.setVisible(false);
            Startgame.invalidate();
            Exitgame.setVisible(false);
            Startgame.invalidate();
            title.setVisible(false);
        
            blue.setVisible(true);
            red.setVisible(true);
            green.setVisible(true);
            pink.setVisible(true);
        
            ColorSequence(); 
            System.out.println("Random Colors: " + answerKey);
        
            flashColors(); 
            Start();  
        }

        if (e.getSource() == blue) {
            playerAnswer.add("Blue");
            numClick++;
            System.out.println("Player Answer: " + playerAnswer);
            getAnswer();
        }
        if (e.getSource() == red) {
            playerAnswer.add("Red");
            numClick++;
            System.out.println("Player Answer: " + playerAnswer);
            getAnswer();
        }

        if (e.getSource() == green) {
            playerAnswer.add("Green");
            numClick++;
            System.out.println("Player Answer: " + playerAnswer);
            getAnswer();
        }
        if (e.getSource() == pink) {
            playerAnswer.add("Pink");
            numClick++;
            System.out.println("Player Answer: " + playerAnswer);
            getAnswer();
        }


    }


}




