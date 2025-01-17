/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2023 The Enola <https://enola.dev> Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.enola.web.rest;

import com.google.common.net.MediaType;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import dev.enola.common.io.resource.MemoryResource;
import dev.enola.common.io.resource.ReadableResource;
import dev.enola.common.io.resource.WritableResource;
import dev.enola.common.protobuf.ProtoIO;
import dev.enola.core.EnolaException;
import dev.enola.core.EnolaService;
import dev.enola.core.IDs;
import dev.enola.core.proto.GetEntityRequest;
import dev.enola.core.proto.ID;
import dev.enola.web.WebHandler;
import dev.enola.web.WebServer;

import java.io.IOException;
import java.net.URI;

public class RestAPI implements WebHandler {

    private final ProtoIO protoIO = new ProtoIO();
    private final EnolaService service;

    public RestAPI(EnolaService service) {
        this.service = service;
    }

    public void register(WebServer server) {
        server.register("/api", this);
    }

    @Override
    public ListenableFuture<ReadableResource> get(URI uri) {
        try {
            var resource = new MemoryResource(MediaType.JSON_UTF_8);
            writeJSON(uri, resource);
            return Futures.immediateFuture(resource);
        } catch (EnolaException | IOException e) {
            return Futures.immediateFailedFuture(e);
        }
    }

    private void writeJSON(URI uri, WritableResource resource) throws EnolaException, IOException {
        var path = uri.getPath();
        if (path.startsWith("/api/entity/")) {
            var idString = path.substring("/api/entity/".length());
            var id = IDs.parse(idString);
            getEntityJSON(id, resource);
        } else {
            // TODO 404 instead 500 (needs API changes)
            throw new IllegalArgumentException("404 - Unknown URI!");
        }
    }

    private void getEntityJSON(ID id, WritableResource resource)
            throws EnolaException, IOException {
        var request = GetEntityRequest.newBuilder().setId(id).build();
        var response = service.getEntity(request);
        var entity = response.getEntity();

        protoIO.write(entity, resource);
    }
}
