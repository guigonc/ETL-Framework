package com.br.guilherme.etlfw.mask.file;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.br.guilherme.etlfw.exceptions.InvalidRegistrySizeException;
import com.br.guilherme.etlfw.exceptions.UnkownRegistryException;
import com.br.guilherme.etlfw.mask.registry.TextRegistryMask;

public class FileMask {

   private String code;
   private String version;
   private String description;
   private Map<String, TextRegistryMask> registryMask;

   public FileMask(String fileCode, String fileVersion, String fileDescription) {
      code = fileCode.toUpperCase(Locale.getDefault());
      version = fileVersion;
      description = fileDescription;
      registryMask = Collections.emptyMap();
   }

   public final String getCode() { return code; }
   
   public String getVersion() { return version; }
   
   public final String getDescription() { return description; }
   
   public final Map<String, TextRegistryMask> getRegistryMasks() { return registryMask; }
   
   public final void addRegistryMask(final TextRegistryMask mask) {
      if (this.registryMask.isEmpty()) {
         this.registryMask = new HashMap<String, TextRegistryMask>();
      }
      this.registryMask.put(mask.getTableName() + mask.getVersion(), mask);
   }

   private TextRegistryMask getRegistry(final String line, final Set<String> keys) {
      TextRegistryMask result = null;
      TextRegistryMask registry;
      String type;
      int size, start, end;

      for (String key : keys) {
         registry = this.registryMask.get(key);
         if (keys.size() == 1) {
            result = registry;
            break;
         }
         size = line.length();
         start = registry.getInitialPosition() - 1;
         end = registry.getFinalPosition();
         if ((start <= end) && (end <= size)) {
            type = line.substring(start, end);
            if (registry.getTableName().equalsIgnoreCase(type)) {
               result = registry;
               break;
            }
         }
      }
      return result;
   }

   public final TextRegistryMask getRegistryWithValues(final String line) throws UnkownRegistryException, InvalidRegistrySizeException {
      Set<String> keys;
      TextRegistryMask result;

      keys = registryMask.keySet();

      result = getRegistry(line, keys);
      if (result == null) {
         throw new UnkownRegistryException(String.format("Unknown Registry <%s>", line));
      }
      return result.getRegistryWithValues(line);
   }
   
}