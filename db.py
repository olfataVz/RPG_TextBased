import os
import mysql.connector

# Koneksi ke database
conn = mysql.connector.connect(
    host="localhost",
    user="root",  # Ganti dengan username database Anda
    password="",  # Ganti dengan password database Anda
    database="inersia"  # Ganti dengan nama database Anda
)
cursor = conn.cursor()

# Fungsi untuk membuat tabel jika belum ada
def create_table():
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS animations (
            id INT AUTO_INCREMENT PRIMARY KEY,
            mob_type VARCHAR(50),
            animation_type VARCHAR(50),
            frame_index INT,
            image_data LONGBLOB
        )
    ''')
    conn.commit()

# Fungsi untuk memasukkan frame animasi ke database
def insert_frames(mob_type, animation_type, folder_path, file_prefix):
    frame_files = sorted(
        [f for f in os.listdir(folder_path) if f.startswith(file_prefix) and f.endswith('.png')]
    )
    for index, file_name in enumerate(frame_files):
        file_path = os.path.join(folder_path, file_name)
        with open(file_path, 'rb') as file:
            image_data = file.read()
            query = '''
                INSERT INTO animations (mob_type, animation_type, frame_index, image_data)
                VALUES (%s, %s, %s, %s)
            '''
            cursor.execute(query, (mob_type, animation_type, index, image_data))
            print(f"Inserted {file_name} into database.")
    conn.commit()

# Fungsi untuk membaca frame animasi dari database
def read_frames(mob_type, animation_type, output_folder):
    query = '''
        SELECT frame_index, image_data FROM animations
        WHERE mob_type = %s AND animation_type = %s
        ORDER BY frame_index
    '''
    cursor.execute(query, (mob_type, animation_type))
    results = cursor.fetchall()

    if not os.path.exists(output_folder):
        os.makedirs(output_folder)

    for frame_index, image_data in results:
        output_path = os.path.join(output_folder, f"{animation_type}{frame_index + 1}.png")
        with open(output_path, 'wb') as file:
            file.write(image_data)
        print(f"Saved frame {frame_index + 1} to {output_path}")

# Fungsi untuk memproses semua animasi untuk satu mob
def process_mob(mob_type, base_folder):
    animations = {
        "Idle": "idle",
        "Walk_Left": "walk-L",
        "Walk_Right": "walk-R",
        "Attack": "attack",
        "Hurt": "hurt",
        "Death": "death"
    }
    for animation_type, file_prefix in animations.items():
        folder_path = os.path.join(base_folder, animation_type)
        if os.path.exists(folder_path):
            insert_frames(mob_type, animation_type, folder_path, file_prefix)
        else:
            print(f"Folder {folder_path} not found. Skipping.")

# Proses semua mob
if __name__ == "__main__":
    create_table()

    # Memasukkan data ke database
    mobs = ["slime", "orc", "golem"]
    base_folder = "D:\smstr 5\PBO\RPG_TextBased\Assets"
    for mob in mobs:
        process_mob(mob, os.path.join(base_folder, mob))

    # Membaca data dari database
    output_base_folder = "D:\smstr 5\PBO\RPG_TextBased\Assets\output_assets"
    for mob in mobs:
        for animation in ["Idle", "Walk_Left", "Walk_Right", "Attack", "Hurt", "Death"]:
            read_frames(mob, animation, os.path.join(output_base_folder, mob, animation))

    # Menutup koneksi
    cursor.close()
    conn.close()
