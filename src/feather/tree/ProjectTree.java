package feather.tree;

import java.awt.Component;
import java.io.File;
import javax.swing.*;
import javax.swing.tree.*;
import static feather.popumenu.RightClickMenu.getTreePath;
import feather.properties.Dirs;
import feather.resources.Icons;

public class ProjectTree {

    File rootFile;
    JTree tree;
    DefaultTreeModel model;

    public JTree getTree() {
        rootFile = new File(Dirs.WORKING_DIRECTORY);
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootFile);
        tree = new JTree(root);
        model = new DefaultTreeModel(root);
        tree.setModel(model);
        addFiles(rootFile, root, model);
        expandAllNodes(tree);
        tree.setRootVisible(false);
        tree.setCellRenderer(new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean isLeaf, int row, boolean focused) {
                Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, isLeaf, row, focused);
                setLeafIcon(Icons.JAVA);
                return c;
            }
        });
        return tree;
    }

    public void addNode(JTree tree, String name) {
        model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        model.insertNodeInto(new DefaultMutableTreeNode(name), root, root.getChildCount());
    }

    public void addProjectNode(JTree tree, String name) {
        model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        DefaultMutableTreeNode proj = new DefaultMutableTreeNode(name);
        DefaultMutableTreeNode src = new DefaultMutableTreeNode("src");
        model.insertNodeInto(proj, root, root.getChildCount());
        model.insertNodeInto(src, proj, proj.getChildCount());
        model.reload(root);
    }

    public void removeNode(JTree tree) {
        String path = getTreePath(tree);
        try {
            File file = new File(path);
            if (file.isDirectory()) {
                deleteDir(file);
            }
            file.delete();
        } catch (Exception e) {
        }

        model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) node.getParent();
        root.remove(node);
        model.reload(root);

    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }

    protected void addFiles(File rootFile, DefaultMutableTreeNode root, DefaultTreeModel model) {
        if (!rootFile.exists()) {
            return;
        }
        for (File file : rootFile.listFiles()) {
            if (file.getName().endsWith(".class")) {
                continue;
            }
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(file.getName());
            model.insertNodeInto(child, root, root.getChildCount());
            root.add(child);

            if (file.isDirectory()) {
                addFiles(file, child, model);
            }
        }

    }

    public void expandAllNodes(JTree tree) {
        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);
        }
    }

}
