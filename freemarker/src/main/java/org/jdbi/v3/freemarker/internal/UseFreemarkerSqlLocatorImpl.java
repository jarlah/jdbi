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
package org.jdbi.v3.freemarker.internal;

import static org.jdbi.v3.freemarker.FreemarkerSqlLocator.findTemplateDirectory;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.jdbi.v3.core.config.ConfigRegistry;
import org.jdbi.v3.core.statement.SqlStatements;
import org.jdbi.v3.core.statement.TemplateEngine;
import org.jdbi.v3.sqlobject.SqlObjects;
import org.jdbi.v3.sqlobject.config.Configurer;
import org.jdbi.v3.sqlobject.internal.SqlAnnotations;
import org.jdbi.v3.sqlobject.locator.SqlLocator;

public class UseFreemarkerSqlLocatorImpl implements Configurer {
    @Override
    public void configureForType(ConfigRegistry registry, Annotation annotation, Class<?> sqlObjectType) {
        SqlLocator locator = (type, method, config) -> {
            String templateName = SqlAnnotations.getAnnotationValue(method, sql -> sql).orElseGet(method::getName);
            File directory = findTemplateDirectory(sqlObjectType);
            if (directory == null 
            		|| !directory.exists() 
            		|| !new File(directory, templateName + ".ftl").exists()) {
                throw new IllegalStateException("No Freemarker template " + templateName + " for class " + sqlObjectType);
            }
            return templateName;
        };
        TemplateEngine templateEngine = (templateName, ctx) -> {
            return null;
        };
        registry.get(SqlObjects.class).setSqlLocator(locator);
        registry.get(SqlStatements.class).setTemplateEngine(templateEngine);
    }

    @Override
    public void configureForMethod(ConfigRegistry registry, Annotation annotation, Class<?> sqlObjectType, Method method) {
        configureForType(registry, annotation, sqlObjectType);
    }
}
