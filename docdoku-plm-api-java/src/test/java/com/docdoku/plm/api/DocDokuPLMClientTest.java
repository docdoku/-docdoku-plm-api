/*
 * DocDoku, Professional Open Source
 * Copyright 2006 - 2017 DocDoku SARL
 *
 * This file is part of DocDokuPLM.
 *
 * DocDokuPLM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DocDokuPLM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with DocDokuPLM.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.docdoku.plm.api;

import com.docdoku.plm.api.client.ApiClient;
import com.docdoku.plm.api.client.ApiException;
import com.docdoku.plm.api.models.AccountDTO;
import com.docdoku.plm.api.models.WorkspaceDTO;
import com.docdoku.plm.api.models.WorkspaceListDTO;
import com.docdoku.plm.api.services.AccountsApi;
import com.docdoku.plm.api.services.WorkspacesApi;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * This class tests DocDokuPLMClient class
 *
 * @Author Morgan Guimard
 */

@RunWith(JUnit4.class)
public class DocDokuPLMClientTest {
    @Test
    public void basicTests() throws ApiException {
        runTest(TestConfig.BASIC_CLIENT);
    }

    @Test
    public void cookieTests() throws ApiException {
        runTest(TestConfig.COOKIE_CLIENT);
    }

    @Test
    public void jwtTests() throws ApiException {
        runTest(TestConfig.JWT_CLIENT);
    }


    private void runTest(ApiClient client) throws ApiException {
        WorkspacesApi workspacesApi = new WorkspacesApi(client);
        AccountsApi accountsApi = new AccountsApi(client);

        AccountDTO account = accountsApi.getAccount();
        Assert.assertEquals(TestConfig.LOGIN, account.getLogin());

        WorkspaceDTO workspace = new WorkspaceDTO();
        workspace.setId(TestUtils.randomString());

        WorkspaceDTO createdWorkspace = workspacesApi.createWorkspace(workspace, TestConfig.LOGIN);
        WorkspaceListDTO workspacesForConnectedUser = workspacesApi.getWorkspacesForConnectedUser();

        Assert.assertNotNull(workspacesForConnectedUser);
        Assert.assertTrue("Should contain created workspace", workspacesForConnectedUser.getAllWorkspaces().contains(createdWorkspace));

        workspacesApi.deleteWorkspace(createdWorkspace.getId());
    }

}
