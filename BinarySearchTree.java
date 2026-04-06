import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

// ================= NODE =================
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

// ================= BST =================
class BST {
    Node root;

    // INSERT
    Node insert(Node root, int id, String nama) {
        if (root == null) return new Node(id, nama);

        if (id < root.id)
            root.left = insert(root.left, id, nama);
        else
            root.right = insert(root.right, id, nama);

        return root;
    }

    // SEARCH
    Node search(Node root, int id) {
        if (root == null || root.id == id) return root;

        if (id < root.id)
            return search(root.left, id);
        else
            return search(root.right, id);
    }

    // MIN VALUE (untuk delete)
    Node minValue(Node node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    // DELETE
    Node delete(Node root, int id) {
        if (root == null) return root;

        if (id < root.id)
            root.left = delete(root.left, id);
        else if (id > root.id)
            root.right = delete(root.right, id);
        else {
            // 1 atau 0 child
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;

            // 2 child
            Node temp = minValue(root.right);

            root.id = temp.id;
            root.nama = temp.nama;

            root.right = delete(root.right, temp.id);
        }
        return root;
    }

    // TRAVERSAL
    void inorder(Node root) {
        if (root != null) {
            inorder(root.left);
            System.out.println(root.id + " | " + root.nama);
            inorder(root.right);
        }
    }

    void preorder(Node root) {
        if (root != null) {
            System.out.println(root.id + " | " + root.nama);
            preorder(root.left);
            preorder(root.right);
        }
    }

    void postorder(Node root) {
        if (root != null) {
            postorder(root.left);
            postorder(root.right);
            System.out.println(root.id + " | " + root.nama);
        }
    }
}

// ================= MAIN =================
public class BinarySearchTree {

    // LOAD CSV
    static void loadCSV(String fileName, BST bst) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;

            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                int id = Integer.parseInt(data[0]);
                String nama = data[1];
              

                bst.root = bst.insert(bst.root, id, nama);
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Gagal membaca CSV: " + e.getMessage());
        }
    }

    // ================= PROGRAM =================
    public static void main(String[] args) {
        BST bst = new BST();
        Scanner sc = new Scanner(System.in);

        // Load data dari CSV (TIDAK langsung tampil)
        loadCSV("data100.xlsx-data_barang.csv", bst);

        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Tambah Data");
            System.out.println("2. Cari Data");
            System.out.println("3. Hapus Data");
            System.out.println("4. Inorder");
            System.out.println("5. Preorder");
            System.out.println("6. Postorder");
            System.out.println("7. Keluar");

            System.out.print("Pilih: ");
            int pilihan = sc.nextInt();

            switch (pilihan) {
                case 1:
                    System.out.print("ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Nama: ");
                    String nama = sc.nextLine();

                    bst.root = bst.insert(bst.root, id, nama);
                    System.out.println("Data berhasil ditambahkan!");
                    break;

                case 2:
                    System.out.print("Cari ID: ");
                    int cari = sc.nextInt();

                    Node hasil = bst.search(bst.root, cari);
                    if (hasil != null) {
                        System.out.println("Ditemukan:");
                        System.out.println(hasil.id + " | " + hasil.nama);
                    } else {
                        System.out.println("Data tidak ditemukan!");
                    }
                    break;

                case 3:
                    System.out.print("Hapus ID: ");
                    int hapus = sc.nextInt();

                    bst.root = bst.delete(bst.root, hapus);
                    System.out.println("Data berhasil dihapus (jika ada)");
                    break;

                case 4:
                    System.out.println("=== INORDER ===");
                    bst.inorder(bst.root);
                    break;

                case 5:
                    System.out.println("=== PREORDER ===");
                    bst.preorder(bst.root);
                    break;

                case 6:
                    System.out.println("=== POSTORDER ===");
                    bst.postorder(bst.root);
                    break;

                case 7:
                    System.out.println("Keluar program...");
                    return;

                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }
}