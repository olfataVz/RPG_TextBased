package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

public class Game {

    String playerName;
    int hp, atk;
    Font gameFont;

    JFrame window;
    JLayeredPane layeredPane;
    JLabel background, titleLogoLabel, slimeLabel, backgroundText, backgroundInfo;
    JPanel startButtonPanel, exitButtonPanel, mainTextPanel, slimePanel;
    JButton startButton, exitButton, choice1, choice2, choice3, choice4;
    JTextArea mainTextArea, mainTextLabel;

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
        } catch (FontFormatException | IOException e) { }

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

        ImageIcon bgInfo = new ImageIcon("Assets/img/bg_info.png"); 
        Image scaledBgInfo = bgInfo.getImage().getScaledInstance(1150, 90, Image.SCALE_SMOOTH);
        backgroundInfo = new JLabel(new ImageIcon(scaledBgInfo));
        backgroundInfo.setBounds(30, 10, 1150, 90); 
        backgroundInfo.setVisible(false);
        layeredPane.add(backgroundInfo, Integer.valueOf(1));

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
                    mainTextPanel.revalidate();
                    mainTextPanel.repaint();
        
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
                mainTextArea.setVisible(false);
                mainTextPanel.remove(nameField);
                mainTextPanel.remove(confirmButton);
                backgroundInfo.setVisible(true);

                JPanel playerInfoPanel = new JPanel();
                playerInfoPanel.setLayout(null); // Menggunakan null layout untuk fleksibilitas posisi
                playerInfoPanel.setBounds(70, 40, 1150, 50); // Ditempatkan lebih atas dari mainTextPanel
                playerInfoPanel.setOpaque(false); // Panel transparan agar background terlihat

                // Membuat JTextArea untuk nama pemain
                JTextArea playerNameText = new JTextArea("Player: " + playerName);
                playerNameText.setBounds(100, 5, 500, 40); // Posisi di kiri atas
                playerNameText.setLineWrap(true);
                playerNameText.setWrapStyleWord(true);
                playerNameText.setEditable(false);
                playerNameText.setOpaque(false);
                playerNameText.setBackground(null);
                playerNameText.setForeground(Color.WHITE);
                playerNameText.setFont(gameFont);

                ImageIcon heartIcon = new ImageIcon(new ImageIcon("Assets/img/heart.png").getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
                ImageIcon attackIcon = new ImageIcon(new ImageIcon("Assets/img/attack.png").getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));

                // Membuat JLabel untuk gambar Heart (HP) dan menambahkan nilai HP
                JLabel heartLabel = new JLabel(heartIcon);
                heartLabel.setBounds(740, -15, 60, 60); // Posisi gambar heart

                JLabel hpTextLabel = new JLabel(" " + hp); // Menambahkan nilai HP setelah gambar
                hpTextLabel.setBounds(780, 0, 100, 35); // Posisi teks HP setelah gambar
                hpTextLabel.setForeground(new Color(184, 18, 18));
                hpTextLabel.setFont(gameFont);

                // Membuat JLabel untuk gambar Attack (ATK) dan menambahkan nilai ATK
                JLabel attackLabel = new JLabel(attackIcon);
                attackLabel.setBounds(880, -15, 60, 60); // Posisi gambar attack

                JLabel atkTextLabel = new JLabel(" " + atk); // Menambahkan nilai ATK setelah gambar
                atkTextLabel.setBounds(920, 0, 100, 35); // Posisi teks ATK setelah gambar
                atkTextLabel.setForeground(new Color(21, 89, 191));
                atkTextLabel.setFont(gameFont);

                // Menambahkan JLabel gambar dan teks ke playerInfoPanel
                playerInfoPanel.add(heartLabel);
                playerInfoPanel.add(hpTextLabel);
                playerInfoPanel.add(attackLabel);
                playerInfoPanel.add(atkTextLabel);
                playerInfoPanel.add(playerNameText);

                // Menambahkan playerInfoPanel ke layeredPane
                layeredPane.add(playerInfoPanel, Integer.valueOf(2));
                
                // Add a new JTextArea for synopsys
                mainTextPanel.setBounds(70, 75, 1150, 550); 
                String sinopsis = "Welcome to the world of Inersia, a universe brimming with mystery and adventure. Here, you will embark on an epic journey as a chosen hero destined to save the land from an encroaching darkness due to the evil boss.";
                mainTextLabel = new JTextArea(sinopsis);
                mainTextLabel.setFont(gameFont);
                mainTextLabel.setForeground(new Color(139, 69, 19)); // Warna SaddleBrown (cokelat tua)
                mainTextLabel.setBounds(140, 150, 900, 100); // Set posisi dan ukuran sesuai kebutuhan
                mainTextLabel.setOpaque(false);
                mainTextLabel.setLineWrap(true);
                mainTextLabel.setWrapStyleWord(true);
                mainTextLabel.setEditable(false);
                layeredPane.add(mainTextLabel, Integer.valueOf(2)); // Add with a higher z-index
                                
                displayTextWithDelay(sinopsis, mainTextLabel, 50, new Runnable() {
                    @Override
                    public void run() {
                     
                        choice1.setVisible(true);
                        choice2.setVisible(true);
                        choice3.setVisible(true);
                        choice4.setVisible(true);
                    }
                });

                
            }
        });

        Slime slime = new Slime(900, 235, 250, 250, layeredPane, this);
        // Setup Choices
        choice1.addActionListener(e -> slime.playIdleAnimation());
        choice2.addActionListener(e -> slime.playWalkLeftAnimation());
        choice3.addActionListener(e -> slime.playHurtAnimation());
        choice4.addActionListener(e -> slime.playDeathAnimation());
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
}