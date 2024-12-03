package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Game {

    String playerName;
    int hp, atk;

    JFrame window;
    JLayeredPane layeredPane;
    JLabel background, titleLogoLabel, slimeLabel;
    JPanel startButtonPanel, exitButtonPanel, mainTextPanel, slimePanel;
    JButton startButton, exitButton, choice1, choice2, choice3, choice4;
    JTextArea mainTextArea;

    // Timer untuk animasi
    Timer animationTimer;
    int frameIndex = 0;
    int slimeX = 900, slimeY = 235; // Posisi awal slime
    int slimeWidth = 250, slimeHeight = 250;

    ImageIcon[] slimeIdleFrames, slimeWalkLeftFrames, slimeAttackFrames, slimeWalkRightFrames, slimeHurtFrames, slimeDeathFrames;

    Font sizeText = new Font("Times New Roman", Font.PLAIN, 36); 
    Font normalText = new Font("Times New Roman", Font.BOLD, 24); 
    Font inputText = new Font("Times New Roman", Font.PLAIN, 18);

    public Game() {
        // Inner Class tsHandler
        class TitleScreenHandler implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent event) {
                createGameScreen();
            }
        }

        TitleScreenHandler tsHandler = new TitleScreenHandler();

        window = new JFrame("INERSIA: RPG Text-Based Game");
        window.setSize(1251, 700);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1251, 700);

        ImageIcon bgImage = new ImageIcon("Assets/img/background.png"); 
        Image scaledImage = bgImage.getImage().getScaledInstance(1251, 700, Image.SCALE_SMOOTH);
        background = new JLabel(new ImageIcon(scaledImage));
        background.setBounds(0, 0, 1251, 700);
        layeredPane.add(background, Integer.valueOf(0));

        ImageIcon logoImage = new ImageIcon("Assets/img/logo.png"); 
        titleLogoLabel = new JLabel(logoImage);
        titleLogoLabel.setBounds(525, -20, 180, 319);
        layeredPane.add(titleLogoLabel, Integer.valueOf(1));

        startButtonPanel = new JPanel();
        startButtonPanel.setOpaque(false); // Transparansi
        startButtonPanel.setBounds(500, 420, 250, 120);

        exitButtonPanel = new JPanel();
        exitButtonPanel.setOpaque(false); 
        exitButtonPanel.setBounds(500, 500, 250, 120);

        // Button
        ImageIcon startIcon = new ImageIcon("Assets/img/start.png");
        Image scaledStartIcon = startIcon.getImage().getScaledInstance(180, 75, Image.SCALE_SMOOTH);
        startButton = new JButton(new ImageIcon(scaledStartIcon));
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);
        startButtonPanel.add(startButton);
        startButton.addActionListener(tsHandler);
        layeredPane.add(startButtonPanel, Integer.valueOf(1));

        ImageIcon exitIcon = new ImageIcon("Assets/img/exit.png");
        Image scaledExitIcon = exitIcon.getImage().getScaledInstance(180, 75, Image.SCALE_SMOOTH);
        exitButton = new JButton(new ImageIcon(scaledExitIcon));
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButtonPanel.add(exitButton);
        exitButton.addActionListener(e -> System.exit(0)); // Fungsi keluar program
        layeredPane.add(exitButtonPanel, Integer.valueOf(1));

        window.add(layeredPane);    
        window.setVisible(true);
    }

    // Method untuk memuat frame animasi dari folder
    private ImageIcon[] loadAnimationFrames(String folderPath, String filePrefix, int frameCount) {
        ImageIcon[] frames = new ImageIcon[frameCount];
        for (int i = 0; i < frameCount; i++) {
            ImageIcon frame = new ImageIcon(folderPath + "/" + filePrefix + (i + 1) + ".png");
            Image scaledFrame = frame.getImage().getScaledInstance(slimeWidth, slimeHeight, Image.SCALE_SMOOTH);
            frames[i] = new ImageIcon(scaledFrame);
        }
        return frames;
    }

    public void createGameScreen() {
        titleLogoLabel.setVisible(false);
        startButton.setVisible(false);
        exitButton.setVisible(false);
    
        // Main text panel settings
        mainTextPanel = new JPanel();
        mainTextPanel.setBounds(70, 100, 1050, 500); 
        mainTextPanel.setBackground(null);
        mainTextPanel.setOpaque(false);
        mainTextPanel.setLayout(null); 
        layeredPane.add(mainTextPanel, Integer.valueOf(1));
    
        mainTextArea = new JTextArea("\tWelcome to Inersia" 
                                    + "\n====================================" 
                                    + "\nPlease Insert Your Name:");
        mainTextArea.setOpaque(false);
        mainTextArea.setForeground(Color.black);
        mainTextArea.setFont(normalText);
        mainTextArea.setLineWrap(true);
        mainTextArea.setWrapStyleWord(true);
        mainTextArea.setEditable(false); 
        mainTextArea.setBounds(300, 120, 500, 120); 
        mainTextPanel.add(mainTextArea);
    
        JTextField nameField = new JTextField();
        nameField.setFont(inputText); 
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setBounds(390, 240, 350, 30); 
        mainTextPanel.add(nameField);
    
        // Confirm button
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setFont(inputText); 
        confirmButton.setBounds(515, 290, 100, 30); 
        mainTextPanel.add(confirmButton);

        JButton nextButton = new JButton("Next >>");
        nextButton.setFont(inputText); 
        nextButton.setBounds(515, 290, 100, 30);
        nextButton.setVisible(false);
        mainTextPanel.add(nextButton);

        choice1 = new JButton("Choice 1");
        choice1.setFont(inputText); 
        choice1.setBounds(515, 350, 100, 25); // Posisi yang berbeda
        choice1.setVisible(false);
        mainTextPanel.add(choice1);

        choice2 = new JButton("Choice 2");
        choice2.setFont(inputText); 
        choice2.setBounds(515, 380, 100, 25); // Posisi yang berbeda
        choice2.setVisible(false);
        mainTextPanel.add(choice2);

        choice3 = new JButton("Choice 3");
        choice3.setFont(inputText); 
        choice3.setBounds(515, 410, 100, 25); // Posisi yang berbeda
        choice3.setVisible(false);
        mainTextPanel.add(choice3);

        choice4 = new JButton("Choice 4");
        choice4.setFont(inputText); 
        choice4.setBounds(515, 440, 100, 25); // Posisi yang berbeda
        choice4.setVisible(false);
        mainTextPanel.add(choice4);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerName = nameField.getText().trim();
                if (!playerName.isEmpty()) {
                    hp = 100;
                    atk = 15;
                    confirmButton.setVisible(false);
                    mainTextPanel.remove(nameField);
                    nameField.setOpaque(false);
                    mainTextPanel.remove(confirmButton);
        
                    String fullText = "\tWelcome to Inersia"
                                    + "\n===================================="
                                    + "\nPlayer " + playerName
                                    + "\nYour Base HP is " + hp + " and ATK is " + atk;
        
                    displayTextWithDelay(fullText, mainTextArea, 50, new Runnable() {
                        @Override
                        public void run() {
                            nextButton.setVisible(true); // Tampilkan tombol setelah teks selesai
                        }
                    });
                } else {
                    displayTextWithDelay("Please enter a valid name to proceed.", mainTextArea, 50, null);
                }
            }
        }); 
        
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextButton.setVisible(false);
                mainTextPanel.remove(nameField);
                mainTextPanel.remove(confirmButton);
                mainTextArea.setBounds(0, 0, 1050, 120); 
                mainTextArea.setText("Player: " + playerName + "\t\t\t\t\tHP: " + hp + " | ATK: " + atk);
                choice1.setVisible(true);
                choice2.setVisible(true);
                choice3.setVisible(true);
                choice4.setVisible(true);
            }
        });

        // Setup Slime Panel
        slimePanel = new JPanel();
        slimePanel.setBounds(slimeX, slimeY, slimeWidth, slimeHeight); 
        slimePanel.setOpaque(false);
        layeredPane.add(slimePanel, Integer.valueOf(1));

        slimeLabel = new JLabel();
        slimePanel.add(slimeLabel);

        // Setup Choices
        choice1.addActionListener(e -> playIdleAnimation());
        choice2.addActionListener(e -> playWalkLeftAnimation());
        choice3.addActionListener(e -> playHurtAnimation());
        choice4.addActionListener(e -> playDeathAnimation());

        loadAnimations();
    }

    private void displayTextWithDelay(String text, JTextArea textArea, int delay, Runnable onComplete) {
        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i < text.length(); i++) {
                    publish(text.substring(0, i + 1)); // Kirim substring terkini
                    Thread.sleep(delay); // Delay per karakter
                }
                return null;
            }
    
            @Override
            protected void process(java.util.List<String> chunks) {
                textArea.setText(chunks.get(chunks.size() - 1)); // Tampilkan teks terbaru
            }
    
            @Override
            protected void done() {
                if (onComplete != null) {
                    onComplete.run(); // Panggil aksi selesai (jika ada)
                }
            }
        };
        worker.execute(); // Jalankan thread
    }    

    private void loadAnimations() {
        slimeIdleFrames = loadAnimationFrames("Assets/slime/Idle", "idle", 6);
        slimeWalkLeftFrames = loadAnimationFrames("Assets/slime/Walk_Left", "walk-L", 16);
        slimeAttackFrames = loadAnimationFrames("Assets/slime/Attack", "attack", 10);
        slimeWalkRightFrames = loadAnimationFrames("Assets/slime/Walk_Right", "walk-R", 16);
        slimeHurtFrames = loadAnimationFrames("Assets/slime/hurt", "hurt", 5);
        slimeDeathFrames = loadAnimationFrames("Assets/slime/death", "death", 10);
    }

    private void playIdleAnimation() {
        stopCurrentAnimation();
        frameIndex = 0;
        animationTimer = new Timer(150, e -> {
            slimeLabel.setIcon(slimeIdleFrames[frameIndex]);
            frameIndex = (frameIndex + 1) % slimeIdleFrames.length;
        });
        animationTimer.start();
    }

    private void playWalkLeftAnimation() {
        stopCurrentAnimation();
        frameIndex = 0;
        animationTimer = new Timer(100, e -> {
            if (frameIndex < slimeWalkLeftFrames.length) {
                slimeLabel.setIcon(slimeWalkLeftFrames[frameIndex]);
                slimeX -= 50;
                slimePanel.setBounds(slimeX, slimeY, slimeWidth, slimeHeight);
                frameIndex++;
            } else {
                animationTimer.stop();
                playAttackAnimation();
            }
        });
        animationTimer.start();
    }

    private void playAttackAnimation() {
        stopCurrentAnimation();
        frameIndex = 0;
        animationTimer = new Timer(150, e -> {
            if (frameIndex < slimeAttackFrames.length) {
                slimeLabel.setIcon(slimeAttackFrames[frameIndex]);
                frameIndex++;
            } else {
                animationTimer.stop();
                playWalkRightAnimation();
            }
        });
        animationTimer.start();
    }

    private void playWalkRightAnimation() {
        stopCurrentAnimation();
        frameIndex = 0;
        animationTimer = new Timer(100, e -> {
            if (frameIndex < slimeWalkRightFrames.length) {
                slimeLabel.setIcon(slimeWalkRightFrames[frameIndex]);
                slimeX += 50;
                slimePanel.setBounds(slimeX, slimeY, slimeWidth, slimeHeight);
                frameIndex++;
            } else {
                animationTimer.stop();
                playIdleAnimation();
            }
        });
        animationTimer.start();
    }

    private void playHurtAnimation() {
        stopCurrentAnimation();
        frameIndex = 0;
        animationTimer = new Timer(200, e -> {
            if (frameIndex < slimeHurtFrames.length) {
                slimeLabel.setIcon(slimeHurtFrames[frameIndex]);
                frameIndex++;
            } else { 
                animationTimer.stop(); 
                playIdleAnimation();
            }
        });
        animationTimer.start();
    }

    private void playDeathAnimation () {
        stopCurrentAnimation();
        frameIndex = 0;
        animationTimer = new Timer(200, e -> {
            if (frameIndex < slimeDeathFrames.length) {
                slimeLabel.setIcon(slimeDeathFrames[frameIndex]);
                frameIndex++;
            } else { 
                animationTimer.stop(); 
                slimeLabel.setVisible(false);
            }
        });
        animationTimer.start();
    }

    private void stopCurrentAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
    }
}
