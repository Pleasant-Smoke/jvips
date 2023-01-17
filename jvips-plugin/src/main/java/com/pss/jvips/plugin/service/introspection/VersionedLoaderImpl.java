/*
 * jvips, a Java implementation that interfaces to libvips
 *
 * Copyright (C) 2023 Jonathan Strauss
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * https://www.gnu.org/licenses/old-licenses/lgpl-2.1-standalone.html
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.pss.jvips.plugin.service.introspection;

import com.pss.jvips.plugin.context.Version;
import com.pss.jvips.plugin.model.xml.Namespace;
import com.pss.jvips.plugin.model.xml.Repository;
import com.pss.jvips.plugin.overrides.VipsOverrides;
import com.pss.jvips.plugin.util.Utils;
import com.pss.jvips.plugin.xml.IgnoreNameSpaceError;
import com.pss.jvips.plugin.xml.NameSpaceFilteringStream;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class VersionedLoaderImpl implements VersionedLoader {


    @Override
    public Namespace loadGObjectIntrospection(Version version) {
        try {
            InputStream stream = new NameSpaceFilteringStream(Utils.getVersionedResourceAsStream(version, "vips.gir"));
            XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
            XMLStreamReader xmlStreamReader = xmlInputFactory
                    .createXMLStreamReader(stream, "UTF-8");
            XMLStreamReader delegate = new IgnoreNameSpaceError(xmlStreamReader);

            JAXBContext jaxbContext = JAXBContext.newInstance(Repository.class);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            Repository repo = (Repository) unmarshaller.unmarshal(delegate);
            return repo.getNamespace();
        } catch (JAXBException | XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public VipsOverrides loadOverrides(Version version) {
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(VipsOverrides.class);
            return  (VipsOverrides) jaxbContext.createUnmarshaller()
                    .unmarshal(Utils.getVersionedResourceAsStream(version, "overrides.xml"));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> loadFunctionWhitelist(Version version){
        try(InputStream resource = Utils.getVersionedResourceAsStream(version, "vips-whitelist.txt")) {
            String value = new String(resource.readAllBytes(), StandardCharsets.UTF_8);
            return Arrays.asList(value.split(","));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Deprecated
    public static Namespace loadXml() {
        try {
            InputStream stream = new NameSpaceFilteringStream(Utils.getResourceAsStream("Vips-8.0.gir"));
            XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
            XMLStreamReader xmlStreamReader = xmlInputFactory
                    .createXMLStreamReader(stream, "UTF-8");
            XMLStreamReader delegate = new IgnoreNameSpaceError(xmlStreamReader);

            JAXBContext jaxbContext = JAXBContext.newInstance(Repository.class);
//            JAXBContext jaxbContext = JAXBContext.newInstance(Repository.class,  NativeArrayType.class, NativeType.class, Factory.class);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            Repository repo = (Repository) unmarshaller.unmarshal(delegate);
            return repo.getNamespace();
        } catch (JAXBException | XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public static List<String> loadFunctionNames(){
        try(InputStream resource = Utils.getResourceAsStream("function-list.txt")) {
            String value = new String(resource.readAllBytes(), StandardCharsets.UTF_8);
            return Arrays.asList(value.split(","));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
