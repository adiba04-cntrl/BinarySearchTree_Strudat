import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class Node {
    int id;
    String nama;
    Node left, right;

    public Node(int id, String nama) {
        this.id = id;
        this.nama = nama;
        left = right = null;
    }
}

class BST {
    Node root;

    BST() {
        root = null;
    }

    // 1. Tambah Data
    void insert(int id, String nama) {
        root = insertRec(root, id, nama);
    }

    Node insertRec(Node root, int id, String nama) {
        if (root == null) {
            root = new Node(id, nama);
            return root;
        }
        if (id < root.id)
            root.left = insertRec(root.left, id, nama);
        else if (id > root.id)
            root.right = insertRec(root.right, id, nama);
        else
            root.nama = nama; // Perbarui jika ID duplikat
        return root;
    }

    // 2. Cari Data
    Node search(int id) {
        Node result = searchRec(root, id);
        if (result != null) {
            System.out.println("Data ditemukan: ID = " + result.id + ", Nama = " + result.nama);
        } else {
            System.out.println("Data dengan ID " + id + " tidak ditemukan.");
        }
        return result;
    }

    Node searchRec(Node root, int id) {
        if (root == null || root.id == id)
            return root;
        if (root.id > id)
            return searchRec(root.left, id);
        return searchRec(root.right, id);
    }

    // 3. Hapus Data
    void delete(int id) {
        root = deleteRec(root, id);
    }

    Node deleteRec(Node root, int id) {
        if (root == null) return root;

        if (id < root.id)
            root.left = deleteRec(root.left, id);
        else if (id > root.id)
            root.right = deleteRec(root.right, id);
        else {
            // Kasus 1 atau 0 child
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;

            // Kasus 2 child: Ambil penerus inorder (terkecil di subtree kanan)
            Node temp = minValueNode(root.right);
            root.id = temp.id;
            root.nama = temp.nama;
            root.right = deleteRec(root.right, temp.id);
        }
        return root;
    }

    Node minValueNode(Node root) {
        Node minv = root;
        while (minv.left != null) {
            minv = minv.left;
        }
        return minv;
    }

    // 4. Traversal
    void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.print("[" + node.id + "] " + node.nama + " -> ");
            inorder(node.right);
        }
    }

    void preorder(Node node) {
        if (node != null) {
            System.out.print("[" + node.id + "] " + node.nama + " -> ");
            preorder(node.left);
            preorder(node.right);
        }
    }

    void postorder(Node node) {
        if (node != null) {
            postorder(node.left);
            postorder(node.right);
            System.out.print("[" + node.id + "] " + node.nama + " -> ");
        }
    }
}

public class BinarySearchTree{
    public static void main(String[] args) {
    BST bst = new BST();
    String file = "data100.xlsx-data_barang.csv"; // Pastikan nama filenya sesuai dengan punyamu ya
    String line;

    // 1. Memuat data dari file saat program pertama kali jalan
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        br.readLine(); // Melewati baris header
        while ((line = br.readLine()) != null) {
            String[] data = line.split(";"); // Memakai titik koma sesuai datamu
            if (data.length >= 2) {
                try {
                    int id = Integer.parseInt(data[0].replace("\"", "").trim());
                    String nama = data[1].replace("\"", "").trim();
                    bst.insert(id, nama);
                } catch (NumberFormatException ex) {
                    // Abaikan baris kosong atau error
                }
            }
        }
        System.out.println("Data dari CSV berhasil dimuat!\n");
    } catch (IOException e) {
        System.out.println("Error membaca file: " + e.getMessage());
    }

    // 2. Membuat Menu Interaktif
    Scanner scanner = new Scanner(System.in);
    int pilihan = 0;

    while (pilihan != 5) {
        System.out.println("\n=== MENU BST DATA BARANG ===");
        System.out.println("1. Tambah Data");
        System.out.println("2. Cari Data");
        System.out.println("3. Hapus Data");
        System.out.println("4. Tampilkan Data (Inorder)");
        System.out.println("5. Keluar");
        System.out.print("Pilih menu (1-5): ");
        
        try {
            pilihan = scanner.nextInt();
            scanner.nextLine(); // Membersihkan sisa enter / newline

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan ID Barang (Angka): ");
                    int idBaru = scanner.nextInt();
                    scanner.nextLine(); // Membersihkan newline
                    System.out.print("Masukkan Nama Barang: ");
                    String namaBaru = scanner.nextLine();
                    bst.insert(idBaru, namaBaru);
                    System.out.println("Data berhasil ditambahkan!");
                    break;
                case 2:
                    System.out.print("Masukkan ID Barang yang dicari: ");
                    int idCari = scanner.nextInt();
                    bst.search(idCari);
                    break;
                case 3:
                    System.out.print("Masukkan ID Barang yang akan dihapus: ");
                    int idHapus = scanner.nextInt();
                    bst.delete(idHapus);
                    System.out.println("Perintah hapus dieksekusi (jika ID ada).");
                    break;
                case 4:
                    System.out.println("--- Data Barang (Inorder) ---");
                    bst.inorder(bst.root);
                    System.out.println("\n-----------------------------");
                    break;
                case 5:
                    System.out.println("Keluar dari program. Terima kasih!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid! Silakan pilih 1-5.");
            }
        } catch (Exception e) {
            System.out.println("Input harus berupa angka!");
            scanner.nextLine(); // Membersihkan input yang salah
        }
    }
    scanner.close();
}
}