package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.swing.*;

enum Stage {
    STAGE_1,
    STAGE_2,
    STAGE_3,
    STAGE_4,

}
public class Game {
    private Stage currentStage = Stage.STAGE_1;

    String playerName, direction;
    Font gameFont;

    JFrame window;
    JLayeredPane layeredPane;
    JLabel background, titleLogoLabel, slimeLabel, backgroundText, backgroundInfo, compassLabel, hpTextLabel, atkTextLabel, backgroundBattleText;
    JPanel startButtonPanel, exitButtonPanel, mainTextPanel, slimePanel;
    JButton startButton, exitButton, choice1, choice2, choice3, choice4;
    JTextArea mainTextArea, synopsisTextArea, battleTextArea;

    private Wizard player;
    private Slime slime;
    private Orc orc;
    private int enemiesDefeated = 0; // Track the number of enemies defeated
    private Object enemy;


    

    public Game() {

        layeredPane = new JLayeredPane();
        slime = new Slime(900, 260, 250, 250, 50, 5, 50, layeredPane, this);
        orc = new Orc(900, 260, 250, 250, 100, 10, 100, layeredPane, this);
        player = new Wizard (90, 215, 250, 250, layeredPane, this, 100, 15, 1);
        class TitleScreenHandler implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent event) {
                createGameScreen();
            }
        }

        try {
            File fontFile = new File("Assets\\font\\manaspc.ttf");
                gameFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(24f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(gameFont);

        } catch (FontFormatException | IOException e) { }

        TitleScreenHandler tsHandler = new TitleScreenHandler();

        // Initialize mainTextArea before setting its font
        mainTextArea = new JTextArea();
        mainTextArea.setFont(gameFont); // Apply the loaded font

        window = new JFrame("INERSIA: RPG Text-Based Game");
        window.setSize(1251, 700);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        ImageIcon battleText = new ImageIcon("Assets/img/Battle_text.png"); 
        Image scaledbattleText = battleText.getImage().getScaledInstance(1100, 370, Image.SCALE_SMOOTH);
        backgroundBattleText = new JLabel(new ImageIcon(scaledbattleText));
        backgroundBattleText.setBounds(70, 150, 1100, 180); 
        backgroundBattleText.setVisible(false);
        layeredPane.add(backgroundBattleText, Integer.valueOf(1));

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
        mainTextArea.setBounds(300, 75, 550, 140); 
        mainTextPanel.add(mainTextArea);

        battleTextArea = new JTextArea();
        battleTextArea.setFont(gameFont);
        battleTextArea.setForeground(new Color(139, 69, 19)); // Warna SaddleBrown (cokelat tua)
        battleTextArea.setBounds(200, 225, 800, 200); // Ukuran lebih kecil dibanding synopsisTextArea
        battleTextArea.setOpaque(false); // Membuatnya transparan
        battleTextArea.setLineWrap(true); // Membungkus teks
        battleTextArea.setWrapStyleWord(true); // Membungkus teks per kata
        battleTextArea.setEditable(false); // Tidak dapat diedit pengguna
        battleTextArea.setVisible(false); // Default tidak terlihat
        layeredPane.add(battleTextArea, Integer.valueOf(2)); 

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

        choice1 = new JButton("i'm ready");
        choice1.setFont(gameFont); 
        choice1.setBounds(470, 400, 180, 25); // Posisi yang berbeda
        choice1.setVisible(false);
        choice1.setForeground(new Color(139, 69, 19));
        mainTextPanel.add(choice1);

        choice1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (choice1.getText()) {
                    case "i'm ready":
                        direction = "map5";
                        
                        synopsisTextArea.setText("Apakah Kamu Yakin Ingin Melanjutkan Perjalanan?");
                        // Change the button text for choices
                        choice1.setText("Ya");
                        choice2.setText("Tidak");
                        choice1.setVisible(true);
                        choice3.setVisible(false);
                        choice4.setVisible(false);
                        
                        // Revalidate and repaint to ensure UI updates
                        mainTextPanel.revalidate();
                        mainTextPanel.repaint();
                        
    
                        // Add action listener for "Ya" and "Tidak" choices
                        choice1.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                if ("Ya".equals(choice1.getText())) {
                                    startFight();
                                }
                            }
                        });
                    break;
                }
            }
        });        

        choice2 = new JButton("go East");
        choice2.setFont(gameFont); 
        choice2.setBounds(470, 430, 180, 25); // Posisi yang berbeda
        choice2.setVisible(false);
        choice2.setForeground(new Color(139, 69, 19));
        mainTextPanel.add(choice2);

        choice3 = new JButton("go South");
        choice3.setFont(gameFont); 
        choice3.setBounds(470, 460, 180, 25); // Posisi yang berbeda
        choice3.setVisible(false);
        choice3.setForeground(new Color(139, 69, 19));
        mainTextPanel.add(choice3);

        choice4 = new JButton("go West");
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
                    confirmButton.setVisible(false);
                    mainTextPanel.remove(nameField);
                    nameField.setOpaque(false);
                    mainTextPanel.remove(confirmButton);
                    mainTextPanel.revalidate();
                    mainTextPanel.repaint();
        
                    String fullText = "\tWelcome to Inersia"
                                    + "\n==============================="
                                    + "\nPlayer " + playerName
                                    + "\nYour Base HP is " + player.getHP() + " and ATK is " + player.getAtk();
        
                    displayTextWithDelay(fullText, mainTextArea, 10, new Runnable() {
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
                playerInfoPanel.setBounds(70, 0, 1150, 100); // Ditempatkan lebih atas dari mainTextPanel
                playerInfoPanel.setOpaque(false); // Panel transparan agar background terlihat
              

                // Membuat JTextArea untuk nama pemain
                JTextArea playerNameText = new JTextArea("Player " + playerName + "  [Lv. " + player.getLevel() + "]");
                playerNameText.setBounds(165, 45, 500, 40); // Posisi di kiri atas
                playerNameText.setLineWrap(true);
                playerNameText.setWrapStyleWord(true);
                playerNameText.setEditable(false);
                playerNameText.setOpaque(false);
                playerNameText.setBackground(null);
                playerNameText.setForeground(Color.WHITE);
                playerNameText.setFont(gameFont);

                playerInfoPanel.add(playerNameText);

                // Menambahkan playerInfoPanel ke layeredPane
                layeredPane.add(playerInfoPanel, Integer.valueOf(2));

                ImageIcon heartIcon = new ImageIcon(new ImageIcon("Assets/img/heart.png").getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
                ImageIcon attackIcon = new ImageIcon(new ImageIcon("Assets/img/attack.png").getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));

                // Membuat JLabel untuk gambar Heart (HP) dan menambahkan nilai HP
                JLabel heartLabel = new JLabel(heartIcon);
                heartLabel.setBounds(740, 25, 60, 60); // Posisi gambar heart

                hpTextLabel = new JLabel(" " + player.getHP()); // Menambahkan nilai HP setelah gambar
                hpTextLabel.setBounds(780, 40, 100, 35); // Posisi teks HP setelah gambar
                hpTextLabel.setForeground(new Color(184, 18, 18));
                hpTextLabel.setFont(gameFont);

                // Membuat JLabel untuk gambar Attack (ATK) dan menambahkan nilai ATK
                JLabel attackLabel = new JLabel(attackIcon);
                attackLabel.setBounds(880, 25, 60, 60); // Posisi gambar attack

                atkTextLabel = new JLabel(" " + player.getAtk()); // Menambahkan nilai ATK setelah gambar
                atkTextLabel.setBounds(920, 40, 100, 35); // Posisi teks ATK setelah gambar
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
                String sinopsis = "Welcome to the world of Inersia, a universe brimming with mystery and adventure. Here, you will embark on an epic journey as a chosen hero destined to save the land from an encroaching darkness due to the evil boss."
                                    + "\n\n\nU are now in the town, You continue walking towards the city of darkness to find the boss. Along the way, you encounter an enemy! Are you ready to fight it?";
                synopsisTextArea = new JTextArea(sinopsis);
                synopsisTextArea.setFont(gameFont);
                synopsisTextArea.setForeground(new Color(139, 69, 19)); // Warna SaddleBrown (cokelat tua)
                synopsisTextArea.setBounds(140, 150, 950, 400); // Set posisi dan ukuran sesuai kebutuhan
                synopsisTextArea.setOpaque(false);
                synopsisTextArea.setLineWrap(true);
                synopsisTextArea.setWrapStyleWord(true);
                synopsisTextArea.setEditable(false);
                layeredPane.add(synopsisTextArea, Integer.valueOf(2)); // Add with a higher z-index
                                
                displayTextWithDelay(sinopsis, synopsisTextArea, 10, new Runnable() {
                    @Override
                    public void run() {
                        choice1.setVisible(true);
                        
                    }
                });
            }
        });
    }

    private void startFight() {
        switch (currentStage) {
            case STAGE_1:
                startFightWithSlime();
                break;
            case STAGE_2:
                startFightWithOrc();
                break;
            case STAGE_3:
                break;
            case STAGE_4:
                break;
            
        }
    }
    

    private void updateStage() {
        switch (currentStage) {
            case STAGE_1:
                currentStage = Stage.STAGE_2;
                break;
            case STAGE_2:
                currentStage = Stage.STAGE_3;
                break;
            case STAGE_3:
                currentStage = Stage.STAGE_4;
                break;
            case STAGE_4:
                //
                break;
            // tambahkan kode untuk mengupdate stage lainnya
        }
    }

    private void startFightWithSlime() {
        synopsisTextArea.setText("Stage1: Pertarungan dengan Slime dimulai!");
        player.playIdleAnimation();
        slime.playIdleAnimation();
        showBattleOptions(slime);
    }

    private void startFightWithOrc() {
        synopsisTextArea.setText("Stage 2: Pertarungan dengan Orc dimulai!");
        player.playIdleAnimation();
        orc.playIdleAnimation();
        showBattleOptions(orc);
    }

    private void showBattleOptions(Object enemy) {
        // Set teks pada tombol
        choice1.setText("Attack");
        choice2.setText("Skill");
        choice3.setText("Ultimate");
        choice4.setText("Healing");
    
        // Tampilkan semua tombol pilihan
        choice1.setVisible(true);
        choice2.setVisible(player.getLevel() >= 2);
        choice3.setVisible(player.getLevel() >= 3);
        choice4.setVisible(player.getLevel() >= 4);

        // Aksi tombol Attack
        choice1.addActionListener(e -> handlePlayerAction("Attack", enemy));
        choice2.addActionListener(e -> handlePlayerAction("Skill", enemy));
        choice3.addActionListener(e -> handlePlayerAction("Ultimate", enemy));
        choice4.addActionListener(e -> handlePlayerAction("Healing", enemy));
    }


    private void handlePlayerAction(String actionType, Object enemy) {
        switch (actionType) {
            case "Attack" -> {
                player.playRunRightAnimation();
                delayAndExecute(1200, () -> {
                    player.playAttackAnimation();
                    if (enemy instanceof Slime) {
                        ((Slime) enemy).reduceHP(player.getAtk());
                        checkEnemyStatus((Slime) enemy);
                    } else if (enemy instanceof Orc) {
                        ((Orc) enemy).reduceHP(player.getAtk());
                        checkEnemyStatus((Orc) enemy);
                    }
                });
            }
            case "Skill" -> {
                player.playSkill();
                delayAndExecute(1200, () -> {
                    if (enemy instanceof Slime) {
                        ((Slime) enemy).reduceHP(player.getAtk() * 2);
                        checkEnemyStatus((Slime) enemy);
                    } else if (enemy instanceof Orc) {
                        ((Orc) enemy).reduceHP(player.getAtk() * 2);
                        checkEnemyStatus((Orc) enemy);
                    }
                });
            }
            case "Ultimate" -> {
                player.playUltimateRight();
                delayAndExecute(1200, () -> {
                    if (enemy instanceof Slime) {
                        ((Slime) enemy).reduceHP(player.getAtk() * 4);
                        checkEnemyStatus((Slime) enemy);
                    } else if (enemy instanceof Orc) {
                        ((Orc) enemy).reduceHP(player.getAtk() * 4);
                        checkEnemyStatus((Orc) enemy);
                    }
                });
            }

            case "Healing" -> {
                player.heal();
                hpTextLabel.setText(" " + player.getHP());
            }
        }
        // Slime menyerang setelah delay 2 detik jika slime masih hidup
        delayAndExecute(3500, () -> {
            if (enemy instanceof Slime && ((Slime) enemy).getHP() > 0) {
                ((Slime) enemy).playWalkLeftAnimation();
                delayAndExecute(1500, () -> {
                    player.reduceHP(((Slime) enemy).getAtk());
                    hpTextLabel.setText(" " + player.getHP());
                });
            } else if (enemy instanceof Orc && ((Orc) enemy).getHP() > 0) {
                ((Orc) enemy).playWalkLeftAnimation();
                delayAndExecute(1500, () -> {
                    player.reduceHP(((Orc) enemy).getAtk());
                    hpTextLabel.setText(" " + player.getHP());
                });
            }
        });

    }

    private void checkEnemyStatus(Mob enemy) {
        if (enemy.getHP() <= 0) {
            enemiesDefeated++;
            delayAndExecute(3000, () -> {
                player.wizardPanel.setVisible(false);
                battleTextArea.setText(enemy instanceof Slime ? "Slime telah dikalahkan!" : "Orc telah dikalahkan!");
                player.levelUp();
                battleOption();
            });
        }
    }
    
    
    private void battleOption() {
        choice1.setText("Lanjut");
        choice1.setVisible(true);
        choice1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.wizardPanel.setVisible(true);
                updateStage();
                startFight();
                choice1.setVisible(false);
            }
        });
    }

    private void delayAndExecute(int delay, Runnable action) {
        Timer timer = new Timer(delay, e -> action.run());
        timer.setRepeats(false); // Timer hanya berjalan sekali
        timer.start();
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