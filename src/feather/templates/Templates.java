package feather.templates;

public class Templates {

	public static String className(String name, boolean isInterface) {
		return isInterface ? "public interface " + name + " {\n\n\n} " : "public class " + name + " {\n\n\n} ";
	}

}
