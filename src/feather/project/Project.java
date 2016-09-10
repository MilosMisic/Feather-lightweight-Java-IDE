package feather.project;

import java.io.File;
import feather.properties.Dirs;

public class Project {

	public Project(String name) {
		new File(Dirs.WORKING_DIRECTORY + "/" + name).mkdir();
		new File(Dirs.WORKING_DIRECTORY + "/" + name + "/src").mkdir();

	}

}
