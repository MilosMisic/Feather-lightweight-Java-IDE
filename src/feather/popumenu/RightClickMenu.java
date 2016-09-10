package feather.popumenu;

import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import feather.templates.Templates;
import feather.tree.ProjectTree;

public class RightClickMenu {

	public void TreeRightClick(MouseEvent evt, JTree projectTree) {
		if (SwingUtilities.isRightMouseButton(evt)) {

			JPopupMenu rightClickMenu = new JPopupMenu();
			rightClickMenu.setSize(200, 200);
			JMenu menu = new JMenu("New");
			JMenuItem newFolder = new JMenuItem("Folder");
			JMenuItem newClass = new JMenuItem("Class");
			JMenuItem setMainProject = new JMenuItem("Set as main project");
			JMenuItem delete = new JMenuItem("Delete");
			rightClickMenu.add(menu);
			menu.add(newClass);
			menu.add(newFolder);
			rightClickMenu.add(setMainProject);
			rightClickMenu.add(delete);

			int row = projectTree.getClosestRowForLocation(evt.getX(), evt.getY());
			projectTree.setSelectionRow(row);
			rightClickMenu.show(evt.getComponent(), evt.getX(), evt.getY());

//*******************************************************************************************\\
			newClass.addActionListener((ActionEvent e) -> {
				String path = getTreePath(projectTree);
				String fileName = JOptionPane.showInputDialog("Enter Class Name");
				if (fileName == null || fileName.equals(""))
					return;
				File file = new File(path + "/" + fileName + ".java");

				try (FileWriter fw = new FileWriter(file)) {
					fw.write(Templates.className(fileName, false));
					fw.close();
				} catch (IOException a) {
				}
				new ProjectTree().addNode(projectTree, file.getName());
			});
//*******************************************************************************************\\		
			newFolder.addActionListener((ActionEvent e) -> {
				String path = getTreePath(projectTree);
				String fileName = JOptionPane.showInputDialog("Enter Folder Name");
				if (fileName == null || fileName.equals(""))
					return;
				File file = new File(path + "/" + fileName);
				file.mkdir();
				new ProjectTree().addNode(projectTree, fileName);
			});

//*******************************************************************************************\\		
			delete.addActionListener((ActionEvent e) -> {
				new ProjectTree().removeNode(projectTree);
			});
		}

	}

	public void consolePaneRightClick(MouseEvent evt, JTextArea console) {
		if (SwingUtilities.isRightMouseButton(evt)) {
			JPopupMenu rightClickMenu = new JPopupMenu();
			rightClickMenu.setSize(200, 200);
			JMenuItem clear = new JMenuItem("Clear");
			rightClickMenu.add(clear);
			rightClickMenu.show(evt.getComponent(), evt.getX(), evt.getY());

			clear.addActionListener((ActionEvent e) -> {
				console.setText("");
			});
		}
	}

	public static String getTreePath(JTree projectTree) {
		String path = "";
		Object[] paths = projectTree.getSelectionPath().getPath();
		for (int i = 0; i < paths.length; i++) {
			path += paths[i];
			if (i + 1 < paths.length) {
				path += File.separator;
			}
		}
		return path;
	}
}
