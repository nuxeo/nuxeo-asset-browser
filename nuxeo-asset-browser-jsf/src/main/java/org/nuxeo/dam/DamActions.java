/*
 * (C) Copyright 2013 Nuxeo SA (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Thomas Roger
 */

package org.nuxeo.dam;

import static org.jboss.seam.ScopeType.CONVERSATION;
import static org.jboss.seam.annotations.Install.FRAMEWORK;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.platform.ui.web.api.NavigationContext;
import org.nuxeo.ecm.platform.ui.web.api.WebActions;

/**
 * @since 5.7
 */
@Name("damActions")
@Scope(CONVERSATION)
@Install(precedence = FRAMEWORK)
public class DamActions implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String MAIN_TABS_DAM = "MAIN_TABS:dam";

    @In(create = true)
    protected NavigationContext navigationContext;

    @In(create = true, required = false)
    protected transient CoreSession documentManager;

    @In(create = true)
    protected transient WebActions webActions;

    public String getSelectedDocumentId() {
        DocumentModel currentDocument = navigationContext.getCurrentDocument();
        return currentDocument != null ? currentDocument.getId() : null;
    }

    public void setSelectedDocumentId(String selectedDocumentId)
            throws ClientException {
        DocumentModel selectedDocument = documentManager.getDocument(new IdRef(
                selectedDocumentId));
        navigationContext.setCurrentDocument(selectedDocument);
    }

    public void selectDocument(DocumentModel doc) throws ClientException {
        navigationContext.setCurrentDocument(doc);
    }

    public String getDamMainTab() {
        return MAIN_TABS_DAM;
    }

    public void setDamMainTab(String tabs) {
        webActions.setCurrentTabIds(!StringUtils.isBlank(tabs) ? tabs
                : MAIN_TABS_DAM);
    }

}
