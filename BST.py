import csv

# ================= NODE =================
class Node:
    def __init__(self, id, nama):
        self.id = int(id)
        self.nama = nama
        self.left = None
        self.right = None


# ================= BST =================
class BST:
    def __init__(self):
        self.root = None

    # INSERT
    def insert(self, root, id, nama):
        if root is None:
            return Node(id, nama)

        if int(id) < root.id:
            root.left = self.insert(root.left, id, nama)
        else:
            root.right = self.insert(root.right, id, nama)

        return root

    # SEARCH
    def search(self, root, id):
        if root is None or root.id == int(id):
            return root

        if int(id) < root.id:
            return self.search(root.left, id)
        else:
            return self.search(root.right, id)

    # MIN VALUE (untuk delete)
    def min_value_node(self, node):
        current = node
        while current.left:
            current = current.left
        return current

    # DELETE
    def delete(self, root, id):
        if root is None:
            return root

        if int(id) < root.id:
            root.left = self.delete(root.left, id)
        elif int(id) > root.id:
            root.right = self.delete(root.right, id)
        else:
            # 1 atau 0 child
            if root.left is None:
                return root.right
            elif root.right is None:
                return root.left

            # 2 child
            temp = self.min_value_node(root.right)
            root.id = temp.id
            root.nama = temp.nama

            root.right = self.delete(root.right, temp.id)

        return root

    # TRAVERSAL
    def inorder(self, root):
        if root:
            self.inorder(root.left)
            print(root.id, "|", root.nama)
            self.inorder(root.right)

    def preorder(self, root):
        if root:
            print(root.id, "|", root.nama)
            self.preorder(root.left)
            self.preorder(root.right)

    def postorder(self, root):
        if root:
            self.postorder(root.left)
            self.postorder(root.right)
            print(root.id, "|", root.nama)


# ================= LOAD CSV =================
def load_csv(filename, bst):
    try:
        with open(filename, mode='r') as file:
            reader = csv.DictReader(file)

            for row in reader:
                bst.root = bst.insert(
                    bst.root,
                    row['id'],
                    row['nama']
                )

    except FileNotFoundError:
        print("File CSV tidak ditemukan!")


# ================= MAIN PROGRAM =================
bst = BST()
load_csv('data100.xlsx-data_barang.csv', bst)

while True:
    print("\n=== MENU ===")
    print("1. Tambah Data")
    print("2. Cari Data")
    print("3. Hapus Data")
    print("4. Inorder")
    print("5. Preorder")
    print("6. Postorder")
    print("7. Keluar")

    pilihan = input("Pilih: ")

    if pilihan == "1":
        id = input("ID: ")
        nama = input("Nama: ")
        bst.root = bst.insert(bst.root, id, nama)
        print("Data berhasil ditambahkan!")

    elif pilihan == "2":
        id = input("Cari ID: ")
        hasil = bst.search(bst.root, id)

        if hasil:
            print("Ditemukan:", hasil.id, "|", hasil.nama)
        else:
            print("Data tidak ditemukan!")

    elif pilihan == "3":
        id = input("Hapus ID: ")
        bst.root = bst.delete(bst.root, id)
        print("Data berhasil dihapus (jika ada)")

    elif pilihan == "4":
        print("=== INORDER ===")
        bst.inorder(bst.root)

    elif pilihan == "5":
        print("=== PREORDER ===")
        bst.preorder(bst.root)

    elif pilihan == "6":
        print("=== POSTORDER ===")
        bst.postorder(bst.root)

    elif pilihan == "7":
        print("Keluar program...")
        break

    else:
        print("Pilihan tidak valid!")