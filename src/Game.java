package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Game {

    String playerName;
    int hp, atk;
    Font gameFont;

    JFrame window;
    JLayeredPane layeredPane;
    JLabel background, titleLogoLabel, slimeLabel, backgroundText;
    JPanel startButtonPanel, exitButtonPanel, mainTextPanel, slimePanel;
    JButton startButton, exitButton, choice1, choice2, choice3, choice4;
    JTextArea mainTextArea;

    // Timer untuk animasi
    Timer animationTimer;
    int frameIndex = 0;
    int slimeX = 900, slimeY = 235; // Posisi awal slime
    int slimeWidth = 250, slimeHeight = 250;

    ImageIcon[] slimeIdleFrames, slimeWalkLeftFrames, slimeAttackFrames, slimeWalkRightFrames, slimeHurtFrames, slimeDeathFrames;

    // Font sizeText = new Font("Times New Roman", Font.PLAIN, 36); 
    // Font normalText = new Font("Times New Roman", Font.BOLD, 24); 
    // Font inputText = new Font("Times New Roman", Font.PLAIN, 18);

    public Game() {
        // Inner Class tsHandler
        class TitleScreenHandler implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent event) {
                createGameScreen();
            }
        }

        try {
            File fontFile = new File("Assets\\font\\manaspc.ttf");
            if (fontFile.exists()) {
                gameFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(24f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(gameFont);
                System.out.println("Font loaded successfully.");
            } else {
                System.out.println("Font file not found at: " + fontFile.getAbsolutePath());
                gameFont = new Font("Arcade Classic", Font.PLAIN, 24); // Fallback font
            }
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            gameFont = new Font("Times New Roman", Font.PLAIN, 24); // Fallback font
            System.out.println("Failed to load custom font, using fallback.");
        }

        TitleScreenHandler tsHandler = new TitleScreenHandler();

        // Initialize mainTextArea before setting its font
        mainTextArea = new JTextArea();
        mainTextArea.setFont(gameFont); // Apply the loaded font

        window = new JFrame("INERSIA: RPG Text-Based Game");
        window.setSize(1251, 700);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1251, 700);

        ImageIcon bgImage = new ImageIcon("Assets/img/background.png"); 
        Image scaledBg = bgImage.getImage().getScaledInstance(1251, 700, Image.SCALE_SMOOTH);
        background = new JLabel(new ImageIcon(scaledBg));
        background.setBounds(0, 0, 1251, 700);
        layeredPane.add(background, Integer.valueOf(0));

        ImageIcon bgText = new ImageIcon("Assets/img/Background_text.png"); 
        Image scaledBgText = bgText.getImage().getScaledInstance(1100, 370, Image.SCALE_SMOOTH);
        backgroundText = new JLabel(new ImageIcon(scaledBgText));
        backgroundText.setBounds(70, 100, 1100, 370); 
        backgroundText.setVisible(false);
        layeredPane.add(backgroundText, Integer.valueOf(1));

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
        startButton.setFont(gameFont);
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);
        startButtonPanel.add(startButton);
        startButton.addActionListener(tsHandler);
        layeredPane.add(startButtonPanel, Integer.valueOf(1));

        ImageIcon exitIcon = new ImageIcon("Assets/img/exit.png");
        Image scaledExitIcon = exitIcon.getImage().getScaledInstance(180, 75, Image.SCALE_SMOOTH);
        exitButton = new JButton(new ImageIcon(scaledExitIcon));
        exitButton.setFont(gameFont);
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
        mainTextPanel.setBounds(70, 75, 1150, 550); 
        mainTextPanel.setBackground(null);
        mainTextPanel.setOpaque(false);
        mainTextPanel.setLayout(null); 
        layeredPane.add(mainTextPanel, Integer.valueOf(2));
        backgroundText.setVisible(true);
        mainTextArea = new JTextArea("\tWelcome to Inersia" 
                                    + "\n===============================" 
                                    + "\nPlease Insert Your Name:");
        mainTextArea.setOpaque(false);
        // mainTextArea.setForeground(new Color(210, 180, 140)); // Warna Tan (cokelat muda)
        mainTextArea.setForeground(new Color(139, 69, 19)); // Warna SaddleBrown (cokelat tua)
        mainTextArea.setFont(gameFont);
        mainTextArea.setLineWrap(true);
        mainTextArea.setWrapStyleWord(true);
        mainTextArea.setEditable(false); 
        mainTextArea.setBounds(300, 75, 500, 120); 
        mainTextPanel.add(mainTextArea);
    
        JTextField nameField = new JTextField();
        nameField.setFont(gameFont); 
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setBounds(390, 240, 350, 30); 
        nameField.setForeground(new Color(139, 69, 19));
        mainTextPanel.add(nameField);
    
        // Confirm button
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setFont(gameFont); 
        confirmButton.setBounds(490, 290, 150, 30); 
        confirmButton.setForeground(new Color(139, 69, 19));
        mainTextPanel.add(confirmButton);

        JButton nextButton = new JButton("Next >>");
        nextButton.setFont(gameFont); 
        nextButton.setBounds(490, 290, 150, 30);
        nextButton.setVisible(false);
        nextButton.setForeground(new Color(139, 69, 19));
        mainTextPanel.add(nextButton);

        choice1 = new JButton("Choice 1");
        choice1.setFont(gameFont); 
        choice1.setBounds(470, 400, 180, 25); // Posisi yang berbeda
        choice1.setVisible(false);
        choice1.setForeground(new Color(139, 69, 19));
        mainTextPanel.add(choice1);

        choice2 = new JButton("Choice 2");
        choice2.setFont(gameFont); 
        choice2.setBounds(470, 430, 180, 25); // Posisi yang berbeda
        choice2.setVisible(false);
        choice2.setForeground(new Color(139, 69, 19));
        mainTextPanel.add(choice2);

        choice3 = new JButton("Choice 3");
        choice3.setFont(gameFont); 
        choice3.setBounds(470, 460, 180, 25); // Posisi yang berbeda
        choice3.setVisible(false);
        choice3.setForeground(new Color(139, 69, 19));
        mainTextPanel.add(choice3);

        choice4 = new JButton("Choice 4");
        choice4.setFont(gameFont); 
        choice4.setBounds(470, 490, 180, 25); // Posisi yang berbeda
        choice4.setVisible(false);
        choice4.setForeground(new Color(139, 69, 19));
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
                                    + "\n==============================="
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
                mainTextPanel.setBounds(80, 60, 1150, 550); 
                mainTextArea.setBounds(0, 0, 1050, 100); 
                mainTextArea.setText("  Player: " + playerName + "\t\t\t\t     HP: " + hp + " | ATK: " + atk);
                
                // Add a new JTextArea for synopsys
                JTextArea mainTextLabel = new JTextArea("Welcome to the world of Inersia, a universe brimming with mystery and adventure. Here, you will embark on an epic journey as a chosen hero destined to save the land from an encroaching darkness due to the evil boss.");
                mainTextLabel.setFont(gameFont);
                mainTextLabel.setForeground(new Color(139, 69, 19)); // Warna SaddleBrown (cokelat tua)
                mainTextLabel.setBounds(140, 150, 900, 100); // Set posisi dan ukuran sesuai kebutuhan
                mainTextLabel.setOpaque(false);
                mainTextLabel.setLineWrap(true);
                mainTextLabel.setWrapStyleWord(true);
                mainTextLabel.setEditable(false);
                layeredPane.add(mainTextLabel, Integer.valueOf(2)); // Add with a higher z-index
                                
                    displayTextWithDelay("Welcome to the world of Inersia, a universe brimming with mystery and adventure. Here, you will embark on an epic journey as a chosen hero destined to save the land from an encroaching darkness due to the evil boss.", mainTextLabel, 50, new Runnable() {
                        @Override
                        public void run() {
                            // nextButton.setVisible(true); // Tampilkan tombol setelah teks selesai
                        }
                    });

                
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
        backgroundText.setVisible(false);
        stopCurrentAnimation();
        frameIndex = 0;
        animationTimer = new Timer(150, e -> {
            slimeLabel.setIcon(slimeIdleFrames[frameIndex]);
            frameIndex = (frameIndex + 1) % slimeIdleFrames.length;
        });
        animationTimer.start();
    }

    private void playWalkLeftAnimation() {
        backgroundText.setVisible(false);
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
        backgroundText.setVisible(false);
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
        backgroundText.setVisible(false);
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
        backgroundText.setVisible(false);
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
        backgroundText.setVisible(false);
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
