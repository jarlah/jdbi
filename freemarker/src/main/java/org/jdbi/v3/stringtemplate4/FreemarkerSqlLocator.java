/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jdbi.v3.stringtemplate4;

import java.io.File;
import java.net.URISyntaxException;

import freemarker.template.Template;

/**
 * Locates SQL in <code>.sql.stg</code> StringTemplate group files on the classpath.
 */
public class FreemarkerSqlLocator {
    
    private FreemarkerSqlLocator() {}

    /**
     * Locates SQL for the given type and name. Example: Given a type <code>com.foo.Bar</code> and a name of
     * <code>baz</code>, loads a StringTemplate group file from the resource named <code>com/foo/Bar.sql.stg</code> on
     * the classpath, and returns the template with the given name from the group.
     *
     * @param type the type that "owns" the given StringTemplate group file. Dictates the filename of the
     *             StringTemplate group file on the classpath.
     * @param name the template name within the StringTemplate group.
     * @return the located SQL.
     */
    public static Template findStringTemplate(Class<?> type, String name) {
        return null;
    }
    
    /**
     * Loads the StringTemplateGroup for the given type. Example: Given a type <code>com.foo.Bar</code>, returns a
     * StringTemplateGroup loaded from the resource named <code>com/foo/Bar.sql.stg</code> on the classpath.
     *
     * @param type the type that "owns" the given StringTemplate group file. Dictates the filename of the
     *             StringTemplate group file on the classpath.
     * @return the loaded StringTemplateGroup.
     * @throws URISyntaxException 
     */
    public static File findTemplateDirectory(Class<?> type) {
        try {
			return new File(type.getClassLoader().getResource(type.getName()).toURI());
		} catch (URISyntaxException e) {
			return null;
		}
    }

}
