import csv

class Node:
    def __init__(self, id_barang, nama):
        self.id = id_barang
        self.nama = nama
        self.left = None
        self.right = None

class BST:
    def __init__(self):
        self.root = None

    # 1. Tambah Data
    def insert(self, id_barang, nama):
        if self.root is None:
            self.root = Node(id_barang, nama)
        else:
            self._insert_recursive(self.root, id_barang, nama)

    def _insert_recursive(self, node, id_barang, nama):
        if id_barang < node.id:
            if node.left is None:
                node.left = Node(id_barang, nama)
            else:
                self._insert_recursive(node.left, id_barang, nama)
        elif id_barang > node.id:
            if node.right is None:
                node.right = Node(id_barang, nama)
            else:
                self._insert_recursive(node.right, id_barang, nama)
        else:
            # Jika ID sudah ada, perbarui nama barang
            node.nama = nama

    # 2. Cari Data
    def search(self, id_barang):
        result = self._search_recursive(self.root, id_barang)
        if result:
            print(f"Data ditemukan: ID = {result.id}, Nama = {result.nama}")
        else:
            print(f"Data dengan ID {id_barang} tidak ditemukan.")
        return result

    def _search_recursive(self, node, id_barang):
        if node is None or node.id == id_barang:
            return node
        if id_barang < node.id:
            return self._search_recursive(node.left, id_barang)
        return self._search_recursive(node.right, id_barang)

    # 3. Hapus Data
    def delete(self, id_barang):
        self.root = self._delete_recursive(self.root, id_barang)

    def _delete_recursive(self, node, id_barang):
        if node is None:
            return node

        if id_barang < node.id:
            node.left = self._delete_recursive(node.left, id_barang)
        elif id_barang > node.id:
            node.right = self._delete_recursive(node.right, id_barang)
        else:
            # Node dengan satu child atau tanpa child
            if node.left is None:
                return node.right
            elif node.right is None:
                return node.left

            # Node dengan dua child: Ambil nilai terkecil di subtree kanan
            temp = self._min_value_node(node.right)
            node.id = temp.id
            node.nama = temp.nama
            node.right = self._delete_recursive(node.right, temp.id)

        return node

    def _min_value_node(self, node):
        current = node
        while current.left is not None:
            current = current.left
        return current

    # 4. Traversal
    def inorder(self, node):
        if node:
            self.inorder(node.left)
            print(f"[{node.id}] {node.nama}", end=" -> ")
            self.inorder(node.right)

    def preorder(self, node):
        if node:
            print(f"[{node.id}] {node.nama}", end=" -> ")
            self.preorder(node.left)
            self.preorder(node.right)

    def postorder(self, node):
        if node:
            self.postorder(node.left)
            self.postorder(node.right)
            print(f"[{node.id}] {node.nama}", end=" -> ")

# --- TESTING PROGRAM ---
# --- TESTING PROGRAM (MENU INTERAKTIF) ---
if __name__ == '__main__':
    bst = BST()
    filename = 'data_baru.csv' # Pastikan nama file ini sama dengan yang ada di foldermu

    # 1. Memuat data dari file saat program pertama kali jalan
    try:
        with open(filename, mode='r', encoding='utf-8') as file:
            reader = csv.reader(file, delimiter=';') # Menggunakan pemisah titik koma
            next(reader, None)  # Melewati baris header
            for row in reader:
                if len(row) >= 2:
                    try:
                        id_barang = int(row[0].strip().replace('"', ''))
                        nama = row[1].strip().replace('"', '')
                        bst.insert(id_barang, nama)
                    except ValueError:
                        pass # Abaikan baris jika ID bukan angka
        print("Data dari CSV berhasil dimuat!\n")
    except FileNotFoundError:
        print(f"Error: File '{filename}' tidak ditemukan.")
        print("Pastikan file berada di folder yang sama dan namanya benar.")

    # 2. Membuat Menu Interaktif
    while True:
        print("\n=== MENU BST DATA BARANG (PYTHON) ===")
        print("1. Tambah Data")
        print("2. Cari Data")
        print("3. Hapus Data")
        print("4. Tampilkan Data (Inorder)")
        print("5. Keluar")
        
        pilihan = input("Pilih menu (1-5): ")
        
        if pilihan == '1':
            try:
                id_baru = int(input("Masukkan ID Barang (Angka): "))
                nama_baru = input("Masukkan Nama Barang: ")
                bst.insert(id_baru, nama_baru)
                print("Data berhasil ditambahkan!")
            except ValueError:
                print("Error: ID harus berupa angka!")
                
        elif pilihan == '2':
            try:
                id_cari = int(input("Masukkan ID Barang yang dicari: "))
                bst.search(id_cari)
            except ValueError:
                print("Error: ID harus berupa angka!")
                
        elif pilihan == '3':
            try:
                id_hapus = int(input("Masukkan ID Barang yang akan dihapus: "))
                bst.delete(id_hapus)
                print("Perintah hapus dieksekusi (jika ID ada di dalam data).")
            except ValueError:
                print("Error: ID harus berupa angka!")
                
        elif pilihan == '4':
            print("--- Data Barang (Inorder) ---")
            if bst.root is None:
                print("Data masih kosong.")
            else:
                bst.inorder(bst.root)
                print("\n-----------------------------")
                
        elif pilihan == '5':
            print("Keluar dari program. Terima kasih!")
            break
            
        else:
            print("Pilihan tidak valid! Silakan ketik angka 1 sampai 5.")