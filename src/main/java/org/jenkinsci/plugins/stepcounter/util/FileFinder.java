package org.jenkinsci.plugins.stepcounter.util;

import java.io.File;
import java.io.IOException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;
import org.jenkinsci.remoting.RoleChecker;

import hudson.FilePath.FileCallable;
import hudson.remoting.VirtualChannel;

/**
 *
 * @author Takuma Ishibashi
 */
public class FileFinder implements FileCallable<String[]> {
	private static final long serialVersionUID = 7132255635577318393L;
	private final String includePattern;

	private final String excludePattern;

	/**
	 * Creates a new instance of {@link FileFinder}.
	 *
	 * @param includePattern
	 *            the ant file pattern to include for finding
	 * @param excludePattern
	 *            the ant file pattern to exclude for finding
	 */
	public FileFinder(final String includePattern, String excludePattern) {
		this.includePattern = includePattern;
		this.excludePattern = excludePattern;
	}

	/**
	 * Returns an array with the filenames of the specified file pattern that
	 * have been found in the workspace.
	 *
	 * @param workspace
	 *            root directory of the workspace
	 * @param channel
	 *            not used
	 * @return the filenames of all found files
	 * @throws IOException
	 *             if the workspace could not be read
	 */
	public String[] invoke(final File workspace, final VirtualChannel channel) throws IOException {
		return find(workspace);
	}

	/**
	 * Returns an array with the filenames of the specified file pattern that
	 * have been found in the workspace.
	 *
	 * @param workspace
	 *            root directory of the workspace
	 * @return the filenames of all found files
	 */
	public String[] find(final File workspace) {
		try {
			FileSet fileSet = new FileSet();
			Project antProject = new Project();
			fileSet.setProject(antProject);
			fileSet.setDir(workspace);
			fileSet.setIncludes(includePattern);
			fileSet.setExcludes(excludePattern);

			return fileSet.getDirectoryScanner(antProject).getIncludedFiles();
		} catch (BuildException exception) {
			return new String[0];
		}
	}

	public void checkRoles(RoleChecker checker) throws SecurityException {
		// TODO 自動生成されたメソッド・スタブ

	}
}