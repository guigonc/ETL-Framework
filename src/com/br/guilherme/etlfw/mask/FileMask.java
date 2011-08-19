package com.br.guilherme.etlfw.mask;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.br.guilherme.etlfw.exceptions.InvalidRegistrySizeException;
import com.br.guilherme.etlfw.exceptions.UnkownRegistryException;

public class FileMask {

   private String code;
   private String version;
   private String description;
   private Map<String, RegistryMask> registryMask;

   public FileMask(String fileCode, String fileVersion, String fileDescription) {
      code = fileCode.toUpperCase(Locale.getDefault());
      version = fileVersion;
      description = fileDescription;
      registryMask = Collections.emptyMap();
   }

   public final String getCode() { return code; }
   public String getVersion() { return version; }
   public final String getDescription() { return description; }
   public final Map<String, RegistryMask> getRegistryMasks() { return registryMask; }

   public final void addRegistryMask(final RegistryMask mask) {
      if (this.registryMask.isEmpty()) {
         this.registryMask = new HashMap<String, RegistryMask>();
      }
      this.registryMask.put(mask.getTableName() + mask.getVersion(), mask);
   }

   private RegistryMask getRegistry(final String line, final Set<String> keys) {
      RegistryMask result = null;
      RegistryMask mask;
      String type;
      int size, start, end;

      for (String key : keys) {
         mask = this.registryMask.get(key);
         if (keys.size() == 1) {
            result = mask;
            break;
         }
         size = line.length();
         start = mask.getInitialPosition() - 1;
         end = mask.getFinalPosition();
         if ((start <= end) && (end <= size)) {
            type = line.substring(start, end);
            if (mask.getTableName().equalsIgnoreCase(type)) {
               result = mask;
               break;
            }
         }
      }
      return result;
   }

   public final RegistryMask getRegistryWithValues(final String line) throws UnkownRegistryException, InvalidRegistrySizeException {
      Set<String> keys;
      RegistryMask result;

      keys = registryMask.keySet();

      result = getRegistry(line, keys);
      if (result == null) {
         throw new UnkownRegistryException(String.format("Unknown Registry <%s>", line));
      }
      return result.getRegistryWithValues(line);
   }
}