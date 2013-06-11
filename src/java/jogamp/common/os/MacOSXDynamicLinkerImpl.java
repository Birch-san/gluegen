/* !---- DO NOT EDIT: This file autogenerated by com\sun\gluegen\JavaEmitter.java on Mon Jul 31 16:27:00 PDT 2006 ----! */

package jogamp.common.os;

import com.jogamp.common.os.DynamicLinker;
import com.jogamp.common.util.SecurityUtil;


public class MacOSXDynamicLinkerImpl implements DynamicLinker {

  public static final long RTLD_DEFAULT = -2;

  public static final int RTLD_LAZY = 0x1;
  public static final int RTLD_NOW = 0x2;
  public static final int RTLD_LOCAL = 0x4;
  public static final int RTLD_GLOBAL = 0x8;

  /** Interface to C language function: <br> <code> int dlclose(void *  __handle); </code>    */
  private static native int dlclose(long __handle);

  /** Interface to C language function: <br> <code> char *  dlerror(void); </code>    */
  private static native java.lang.String dlerror();

  /** Interface to C language function: <br> <code> void *  dlopen(const char *  __path, int __mode); </code>    */
  private static native long dlopen(java.lang.String __path, int __mode);

  /** Interface to C language function: <br> <code> void *  dlsym(void *  __handle, const char *  __symbol); </code>    */
  private static native long dlsym(long __handle, java.lang.String __symbol);


  // --- Begin CustomJavaCode .cfg declarations
  public long openLibraryLocal(String pathname, boolean debug) throws SecurityException {
    // Note we use RTLD_LOCAL visibility to _NOT_ allow this functionality to
    // be used to pre-resolve dependent libraries of JNI code without
    // requiring that all references to symbols in those libraries be
    // looked up dynamically via the ProcAddressTable mechanism; in
    // other words, one can actually link against the library instead of
    // having to dlsym all entry points. System.loadLibrary() uses
    // RTLD_LOCAL visibility so can't be used for this purpose.
    SecurityUtil.checkLinkPermission(pathname);
    return dlopen(pathname, RTLD_LAZY | RTLD_LOCAL);
  }
  
  public long openLibraryGlobal(String pathname, boolean debug) throws SecurityException {
    // Note we use RTLD_GLOBAL visibility to allow this functionality to
    // be used to pre-resolve dependent libraries of JNI code without
    // requiring that all references to symbols in those libraries be
    // looked up dynamically via the ProcAddressTable mechanism; in
    // other words, one can actually link against the library instead of
    // having to dlsym all entry points. System.loadLibrary() uses
    // RTLD_LOCAL visibility so can't be used for this purpose.
    SecurityUtil.checkLinkPermission(pathname);
    return dlopen(pathname, RTLD_LAZY | RTLD_GLOBAL);
  }
  
  public long lookupSymbol(long libraryHandle, String symbolName) {
    final long addr = dlsym(libraryHandle, symbolName);
    if(DEBUG_LOOKUP) {
        System.err.println("MaxOSXDynamicLinkerImpl.lookupSymbol(0x"+Long.toHexString(libraryHandle)+", "+symbolName+") -> 0x"+Long.toHexString(addr));
    }
    return addr;    
  }
  
  public long lookupSymbolGlobal(String symbolName) {
    final long addr = dlsym(RTLD_DEFAULT, symbolName);
    if(DEBUG_LOOKUP) {
        System.err.println("MacOSXDynamicLinkerImpl.lookupSymbolGlobal("+symbolName+") -> 0x"+Long.toHexString(addr));
    }
    return addr;    
  }
  
  public void closeLibrary(long libraryHandle) {
    dlclose(libraryHandle);
  }
  // ---- End CustomJavaCode .cfg declarations

} // end of class MacOSXDynamicLinkerImpl
