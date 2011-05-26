/*
 * javasniff
 * (Based on PHP Client Sniffer (phpsniff))
 * 
 * v 1.0.3 - Apr 19, 2007
 *           - improved Windows pattern
 *           - improved minorversion matching
 *           - added OTHER platform classification
 *           - added OTHER browser classification
 *           - improved Netscape version identification
 *           - improved MSIE version identification
 * v 1.0.2 - Apr 10, 2007
 *           - Added Windows Vista matching
 *           - Maven project
 * v 1.0.1 - Jan, 2006
 *           - addition of Safari on Intel chips
 * v 1.0.0 - Nov 1, 2005
 *           - Conversion from PHP 
 *           
 *  Copyright (C) 2007 Stephen Smith
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 * ----------------------------------------------
 * Original PHP Client Sniffer (phpsniff) header:
 * ----------------------------------------------
 * http://phpsniff.sourceforge.net
 * author Roger Raymond <epsilon7@users.sourceforge.net>
 * version $Id: phpSniff.class.php,v 1.22 2004/04/27 00:55:49 epsilon7 Exp $
 * copyright Copyright &copy; 2002-2004 Roger Raymond
 * license http://opensource.org/licenses/lgpl-license.php GNU Lesser General Public License
 *
 */
package com.strutstool.browser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.xwork.NullArgumentException;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * A server side class which determines the client's web browser environment
 * based on the user-agent field of the request object <br />
 * Notes:
 * <ul>
 * <li>Many browsers will try to impersonate other browsers, specifically MS IE
 * since so many websites have been written poorly and only target IE, refusing
 * to work with other browsers. While identifying as MS IE is not strictly true,
 * it is needed, practical and accepted by this tool.</li>
 * <li>UserAgents which do not correspond to implemented browser ids are
 * classified as OTHER. They may be robots or low uptake browsers. Version
 * information, platform, OS information will not be available</li>
 * <li>Traditional Netscape (such as 4.0, 4.5 etc..) identifies as Mozilla.
 * Netscape classification here refers only to the rebranded Mozilla open-source
 * product via AOL etc...</li>
 * </ul>
 * 
 * @author Stephen Smith
 * @version $Revision: 1.0.3 $ $Date: 2007/04/19 11:22:00 $
 */
public class BrowserSniffer implements Serializable {

    private static final long serialVersionUID = 1L;

    protected final static Log log = LogFactory.getLog(BrowserSniffer.class);
    
    public static final String REQUEST_HEADER_USER_AGENT = "User-Agent";
    
    private static HashMap Browsers = null;
    private static StringBuffer BrowserRegExBuf = null;
    private static Pattern BrowserTypePat = null;
    private static Pattern MinorVersionPat = null;
    private static Pattern PunctuationOnlyPat = null;
    private static Pattern NumberRetrievePat = null;
    private static Pattern NameOnlyPat = null;
    
    private static Pattern WindowsPat = null;
    private static Pattern MacPat = null;
    private static Pattern Os2Pat = null;
    private static Pattern SunosPat = null;
    private static Pattern IrixPat = null;
    private static Pattern HpuxPat = null;
    private static Pattern AixPat = null;
    private static Pattern DecPat = null;
    private static Pattern VmsPat = null;
    private static Pattern ScoPat = null;
    private static Pattern LinuxPat = null;
    private static Pattern BsdPat = null;
    private static Pattern AmigaPat = null;
    private static Pattern AmigaVerPat = null;
    
    public static final String PLATFORM_WIN = "win"; 
    public static final String PLATFORM_AMIGA = "amiga"; 
    public static final String PLATFORM_OS2 = "os2"; 
    public static final String PLATFORM_MAC = "mac"; 
    public static final String PLATFORM_UNIX = "*nix"; 
    public static final String PLATFORM_OTHER = "other"; 
    
    public static final String BROWSER_IE = "IE";
    public static final String BROWSER_NETSCAPE = "NS";
    public static final String BROWSER_GALEON = "GA";
    public static final String BROWSER_PHOENIX = "PX";
    public static final String BROWSER_FIREBIRD = "FB";
    public static final String BROWSER_FIREFOX = "FX";
    public static final String BROWSER_CHIMERA = "CH";
    public static final String BROWSER_CAMINO = "CA";
    public static final String BROWSER_EPIPHANY = "EP";
    public static final String BROWSER_SAFARI = "SF";
    public static final String BROWSER_KMELEON = "KM";
    public static final String BROWSER_MOZILLA = "MZ";
    public static final String BROWSER_OPERA = "OP";
    public static final String BROWSER_KONQUEROR = "KQ";
    public static final String BROWSER_ICAB = "IC";
    public static final String BROWSER_LYNX = "LX";
    public static final String BROWSER_LINKS = "LI";
    public static final String BROWSER_MOSAIC = "MO";
    public static final String BROWSER_AMAYA = "AM";
    public static final String BROWSER_OMNIWEB = "OW";
    public static final String BROWSER_HOTJAVA = "HJ";
    public static final String BROWSER_BROWSEX = "BX";
    public static final String BROWSER_AMIGAVOYAGER = "AV";
    public static final String BROWSER_AMIGAAWEB = "AW";
    public static final String BROWSER_IBROWSE = "IB";

    public static final String BROWSER_OTHER = "~~";

    public static final String MSIE_ID = "msie";
    public static final String OPERA_ID = "opera";
    public static final String NETSCAPE_ID = "netscape";
    
    static {
        Browsers = new HashMap();
        Browsers.put("microsoft internet explorer", BROWSER_IE);
        Browsers.put(MSIE_ID, BROWSER_IE);
        Browsers.put(NETSCAPE_ID, BROWSER_NETSCAPE);
        Browsers.put("galeon", BROWSER_GALEON);
        Browsers.put("phoenix", BROWSER_PHOENIX);
        Browsers.put("mozilla firebird", BROWSER_FIREBIRD);
        Browsers.put("firebird", BROWSER_FIREBIRD);
        Browsers.put("firefox", BROWSER_FIREFOX);
        Browsers.put("chimera", BROWSER_CHIMERA);
        Browsers.put("camino", BROWSER_CAMINO);
        Browsers.put("epiphany", BROWSER_EPIPHANY);
        Browsers.put("safari", BROWSER_SAFARI);
        Browsers.put("k-meleon", BROWSER_KMELEON);
        Browsers.put("mozilla", BROWSER_MOZILLA);
        Browsers.put(OPERA_ID, BROWSER_OPERA);
        Browsers.put("konqueror", BROWSER_KONQUEROR);
        Browsers.put("icab", BROWSER_ICAB);
        Browsers.put("lynx", BROWSER_LYNX);
        Browsers.put("links", BROWSER_LINKS);
        Browsers.put("ncsa mosaic", BROWSER_MOSAIC);
        Browsers.put("amaya", BROWSER_AMAYA);
        Browsers.put("omniweb", BROWSER_OMNIWEB);
        Browsers.put("hotjava", BROWSER_HOTJAVA);
        Browsers.put("browsex", BROWSER_BROWSEX);
        Browsers.put("amigavoyager", BROWSER_AMIGAVOYAGER);
        Browsers.put("amiga-aweb", BROWSER_AMIGAAWEB);
        Browsers.put("ibrowse", BROWSER_IBROWSE);
        
        // build regex
        BrowserRegExBuf = new StringBuffer();
        CollectionUtils.forAllDo(Browsers.keySet(), new Closure() {
            public void execute(Object obj) {
                if (BrowserRegExBuf.length() > 0)
                    BrowserRegExBuf.append('|');
                BrowserRegExBuf.append((String)obj);
                if (((String)obj).equals(NETSCAPE_ID)) {
                	BrowserRegExBuf.append("[6]?");
                }
            }
        });
        BrowserRegExBuf.insert(0, '(');
        BrowserRegExBuf.append(")");
        BrowserRegExBuf.append("[\\/\\sa-z\\(]*([0-9]+)([\\.0-9a-z]+)?");	// browser version string
        
        try {
            BrowserTypePat = Pattern.compile(BrowserRegExBuf.toString(), Pattern.CASE_INSENSITIVE);
            BrowserRegExBuf = null;

            MinorVersionPat = Pattern.compile("([.0-9]+)?([\\.a-z0-9]+)?", Pattern.CASE_INSENSITIVE);
            
            PunctuationOnlyPat = Pattern.compile("[\\p{Punct}]+");
            
            NumberRetrievePat = Pattern.compile("\\D*(\\d+)");
            
            NameOnlyPat = Pattern.compile("(\\D+)\\d*");
            
            WindowsPat  = Pattern.compile("((?:dar){0}win(?:dows)*){1}+(?:(?:[\\s]+([0-9a-z]*)[\\s]*([a-z0-9.]*))|([0-9a-z]{1,3}))", Pattern.CASE_INSENSITIVE);
            MacPat      = Pattern.compile("(68[k0]{1,3})|(ppc mac os x)|(intel mac os x)|([p\\S]{1,5}pc)|(darwin)", Pattern.CASE_INSENSITIVE);
            Os2Pat      = Pattern.compile("os\\/2|ibm-webexplorer", Pattern.CASE_INSENSITIVE);
            SunosPat    = Pattern.compile("(sun|i86)[os\\s]*([0-9]*)", Pattern.CASE_INSENSITIVE);
            IrixPat     = Pattern.compile("(irix)[\\s]*([0-9]*)", Pattern.CASE_INSENSITIVE);
            HpuxPat     = Pattern.compile("(hp-ux)[\\s]*([0-9]*)", Pattern.CASE_INSENSITIVE);
            AixPat      = Pattern.compile("aix([0-9]*)", Pattern.CASE_INSENSITIVE);
            DecPat      = Pattern.compile("dec|osfl|alphaserver|ultrix|alphastation", Pattern.CASE_INSENSITIVE);
            VmsPat      = Pattern.compile("vax|openvms", Pattern.CASE_INSENSITIVE);
            ScoPat      = Pattern.compile("sco|unix_sv", Pattern.CASE_INSENSITIVE);
            LinuxPat    = Pattern.compile("x11|inux", Pattern.CASE_INSENSITIVE);
            BsdPat      = Pattern.compile("(free)?(bsd)", Pattern.CASE_INSENSITIVE);
            AmigaPat    = Pattern.compile("amiga[os]?", Pattern.CASE_INSENSITIVE);
            AmigaVerPat = Pattern.compile("(AmigaOS [\\.1-9]?)", Pattern.CASE_INSENSITIVE);
        }
        catch (Exception e) {
            log.error(e);
        }
    }
    
    private String ua = StringUtils.EMPTY;
    
    public String longName = StringUtils.EMPTY;
    public String browserName = StringUtils.EMPTY;
    public String majorVersion = StringUtils.EMPTY;
    public String minorVersion = StringUtils.EMPTY;
    public String revisionVersion = StringUtils.EMPTY;
    
    public String platform = StringUtils.EMPTY;
    public String os = StringUtils.EMPTY;
    
    /**
     * Cannot invoke default constructor 
     */
    private BrowserSniffer() {
    }
    
    /**
     * Create a new <tt>BrowserSniffer</tt>
     * 
     * @param req the {@link HttpServletRequest} from which to grab the user-agent field
     */
    public BrowserSniffer(HttpServletRequest req) {
        if (req == null) {
            throw new NullArgumentException("BrowserSniffer must have a valid argument");
        }
        
        ua = req.getHeader( REQUEST_HEADER_USER_AGENT );
        if (StringUtils.isNotBlank(ua)) {
            ua = ua.toLowerCase();
            
            try {
                sniffBrowser();
                sniffOS();
            }
            catch (Exception e) {
                log.error(e);
            }
        }
        else {
            ua = StringUtils.EMPTY;
        }
    }
    
    /**
     * @return Returns the user-agent string.
     */
    public String getUa() {
        return ua;
    }

    /**
     * @return Returns the browserName.
     */
    public String getBrowserName() {
        return browserName;
    }
    /**
     * @return Returns the longName.
     */
    public String getLongName() {
        return longName;
    }
    /**
     * @return Returns the majorVersion.
     */
    public String getMajorVersion() {
        return majorVersion;
    }
    /**
     * @return Returns the minorVersion.
     */
    public String getMinorVersion() {
        return minorVersion;
    }
    /**
     * @return Returns the os.
     */
    public String getOs() {
        return os;
    }
    /**
     * @return Returns the platform.
     */
    public String getPlatform() {
        return platform;
    }
    /**
     * @return Returns the revisionVersion.
     */
    public String getRevisionVersion() {
        return revisionVersion;
    }
    
    public boolean isBrowser(String browserID) {
        if (StringUtils.isNotBlank(browserName)) {
            final String id = (String)Browsers.get(browserName);
            return StringUtils.equals(browserID, id);
        }
        else if (StringUtils.equals(BROWSER_OTHER, browserID))
        	return true;
        return false;
    }
    
    public boolean isPlatform(String platformID) {
        if (platform != null) {
            return StringUtils.equals(platformID, platform);
        }
        return false;
    }
    
    public boolean isWindows() {
        return isPlatform(PLATFORM_WIN);
    }
    
    public boolean isMac() {
        return isPlatform(PLATFORM_MAC);
    }
    
    public boolean isIE() {
        return isBrowser(BROWSER_IE);
    }
    
    public boolean isSafari() {
        return isBrowser(BROWSER_SAFARI);
    }
    
    public boolean isOpera() {
        return isBrowser(BROWSER_OPERA);
    }
    
    public boolean isNetscape() {
        return isBrowser(BROWSER_NETSCAPE);
    }
    
    public boolean isMozilla() {
        return isBrowser(BROWSER_MOZILLA);
    }
    
    public boolean isFirefox() {
        return isBrowser(BROWSER_FIREFOX);
    }

    // isGecko (firefox, bird, phoenix, mozilla (version >=6), camino etc...)
    // isSafari (safari, omniweb etc...)
    
    private void sniffOS() throws Exception {
        // look for Windows Box
        // eg: Windows NT 5.0
        // [0] = Windows NT 5.0
        // [1] = Windows
        // [2] = NT
        // [3] = 5.0
        ArrayList matches = getMatches(WindowsPat, ua, 4);
        if (!matches.isEmpty()) {
            String[] versionParticulars = (String[])matches.get(0);
            String v1 = versionParticulars[2];
            String v2 = versionParticulars[3];
            
            // Establish NT 6.0 as Windows Vista
            if (StringUtils.contains(v1, "nt") && StringUtils.equals(v2, "6.0"))
                v1 = "vista";
            // Establish NT 5.2 as Windows Server 2003 or XP 64
            else if (StringUtils.contains(v1, "nt") && StringUtils.equals(v2, "5.2"))
                v1 = "2003";
            // Establish NT 5.1 as Windows XP
            else if (StringUtils.contains(v1, "nt") && StringUtils.equals(v2, "5.1"))
                v1 = "xp";
            // Establish NT 5.0 and Windows 2000 as win2k
            else if (StringUtils.equals(v1, "2000"))
                v1 = "2000";
            else if (StringUtils.contains(v1, "nt") && StringUtils.contains(v2, "5.0"))
                v1 = "2000";
            // Establish NT 4.0 as winnt
            else if (StringUtils.contains(v1, "nt") && 
            		(StringUtils.contains(v2, "4.0") || StringUtils.contains(v2, "3.51") ||
            		StringUtils.contains(v2, "3.5") || StringUtils.contains(v2, "3.1")))
                v1 = "nt";
            // Establish 9x 4.90 as Windows 98
            else if (StringUtils.contains(v1, "9x") && StringUtils.equals(v2, "98"))
                v1 = "98";
            // See if we're running windows 3.1
            else if (StringUtils.equals(StringUtils.join(new String[] {v1, v2}), "16bit"))
                v1 = "31";
            // otherwise display as is (31,95,98,NT,ME,XP)
            else
                v1 = StringUtils.join(new String[] {v1, v2});
            
            if (StringUtils.isEmpty(v1)) {
            	final Matcher matcher = NumberRetrievePat.matcher(versionParticulars[0]);
            	if (matcher.matches()) {
            		v1 = matcher.group(matcher.groupCount());
            		if (StringUtils.contains(versionParticulars[0], "nt"))
            			v1 = "nt";
            	}
            	else {
            		v1 = PLATFORM_WIN;
            	}
            }
            
            os = v1;
            platform = PLATFORM_WIN;
            return;
        }
        
        // look for amiga OS
        // eg: Amiga-AWeb/3.5.07 beta
        // [0] = Amiga
        matches = getMatches(AmigaPat, ua, 1);
        if (!matches.isEmpty()) {
            platform = PLATFORM_AMIGA;
            if (StringUtils.contains(ua, "morphos")) 
                os = "morphos";
            else if (StringUtils.contains(ua, "mc680x0"))
                os = "mc680x0";
            else if (StringUtils.contains(ua, "ppc"))
                os = "ppc";
            else {
                matches = getMatches(AmigaVerPat, ua, 2);
                if (!matches.isEmpty()) {
                    int count = matches.size() - 1;
                    String[] versionParticulars = (String[])matches.get(count);
                    os = versionParticulars[1];
                }
            }
            
            return;
        }
        
        // look for OS2
        matches = getMatches(Os2Pat, ua, 1);
        if (!matches.isEmpty()) {
            platform = PLATFORM_OS2;
            os = PLATFORM_OS2;
            return;
        }
        
        // look for mac
        // sets: platform = mac ; os = 68k or ppc
        matches = getMatches(MacPat, ua, 5);
        if (!matches.isEmpty()) {
            platform = PLATFORM_MAC;

            int count = matches.size() - 1;
            String[] versionParticulars = (String[])matches.get(count);
            os = (StringUtils.isNotEmpty(versionParticulars[1])) ? "68k" : StringUtils.EMPTY;
            os = (StringUtils.isNotEmpty(versionParticulars[2])) ? "osx" : os;
            os = (StringUtils.isNotEmpty(versionParticulars[3])) ? "osx" : os;
            os = (StringUtils.isNotEmpty(versionParticulars[4])) ? "ppc" : os;
            return;
        }
        
        //  look for *nix boxes
        //  sunos sets: platform = *nix ; os = sun|sun4|sun5|suni86
        matches = getMatches(SunosPat, ua, 3);
        if (!matches.isEmpty()) {
            platform = PLATFORM_UNIX;
            
            int count = matches.size() - 1;
            String[] versionParticulars = (String[])matches.get(count);
            if (!StringUtils.contains("sun", versionParticulars[1]))
                versionParticulars[1] = StringUtils.join(new String[] {"sun", versionParticulars[1]});
            os = StringUtils.join(new String[] {versionParticulars[1], versionParticulars[2]});
            return;
        }
        
        // irix sets: platform = *nix ; os = irix|irix5|irix6|...
        matches = getMatches(IrixPat, ua, 3);
        if (!matches.isEmpty()) {
            platform = PLATFORM_UNIX;
            
            int count = matches.size() - 1;
            String[] versionParticulars = (String[])matches.get(count);
            os = StringUtils.join(new String[] {versionParticulars[1], versionParticulars[2]});
            return;
        }
        
        // hp-ux sets: platform = *nix ; os = hpux9|hpux10|...
        matches = getMatches(HpuxPat, ua, 3);
        if (!matches.isEmpty()) {
            platform = PLATFORM_UNIX;
            
            int count = matches.size() - 1;
            String[] versionParticulars = (String[])matches.get(count);
            versionParticulars[1] = StringUtils.replace(versionParticulars[1], "-", StringUtils.EMPTY);
            os = StringUtils.join(new String[] {versionParticulars[1], StringUtils.trim(versionParticulars[2])});
            return;
        }
        
        // aix sets: platform = *nix ; os = aix|aix1|aix2|aix3|...
        matches = getMatches(AixPat, ua, 2);
        if (!matches.isEmpty()) {
            platform = PLATFORM_UNIX;

            int count = matches.size() - 1;
            String[] versionParticulars = (String[])matches.get(count);
            os = StringUtils.join(new String[] {"aix", versionParticulars[1]});
            return;
        }
        
        // dec sets: platform = *nix ; os = dec
        matches = getMatches(DecPat, ua, 1);
        if (!matches.isEmpty()) {
            platform = PLATFORM_UNIX;
            os = "dec";
            return;
        }
        
        // vms sets: platform = *nix ; os = vms
        matches = getMatches(VmsPat, ua, 1);
        if (!matches.isEmpty()) {
            platform = PLATFORM_UNIX;
            os = "vms";
            return;
        }
        
        // sco sets: platform = *nix ; os = sco
        matches = getMatches(ScoPat, ua, 1);
        if (!matches.isEmpty()) {
            platform = PLATFORM_UNIX;
            os = "sco";
            return;
        }
        
        // unixware sets: platform = *nix ; os = unixware
        if (StringUtils.contains(ua, "'unix_system_v'")) {
            platform = PLATFORM_UNIX;
            os = "unixware";
            return;
        }
        
        // mpras sets: platform = *nix ; os = mpras
        if (StringUtils.contains(ua, "'ncr'")) {
            platform = PLATFORM_UNIX;
            os = "mpras";
            return;
        }
        
        // reliant sets: platform = *nix ; os = reliant
        if (StringUtils.contains(ua, "'reliantunix'")) {
            platform = PLATFORM_UNIX;
            os = "reliant";
            return;
        }
        
        // sinix sets: platform = *nix ; os = sinix
        if (StringUtils.contains(ua, "'sinix'")) {
            platform = PLATFORM_UNIX;
            os = "sinix";
            return;
        }
        
        // bsd sets: platform = *nix ; os = bsd|freebsd
        matches = getMatches(BsdPat, ua, 3);
        if (!matches.isEmpty()) {
            platform = PLATFORM_UNIX;
            
            int count = matches.size() - 1;
            String[] versionParticulars = (String[])matches.get(count);
            os = StringUtils.join(new String[] {versionParticulars[1], versionParticulars[2]});
            return;
        }
        
        // linux sets: platform = *nix ; os = linux
        matches = getMatches(LinuxPat, ua, 1);
        if (!matches.isEmpty()) {
            platform = PLATFORM_UNIX;
            os = "linux";
            return;
        }
        
        platform = PLATFORM_OTHER;
        os = "unknown";
    }

    private void sniffBrowser() throws Exception {
        // eg: Camino/0.7
        // [0] = Camino/0.7
        // [1] = Camino
        // [2] = 0
        // [3] = .7
        ArrayList matches = getMatches(BrowserTypePat, ua, 4);
        if (matches.isEmpty())
            return;
        
        // first find out whether it's msie hiding behind many different doors... 
        String[] browserParticulars = (String[]) CollectionUtils.find(matches, new Predicate() {
			public boolean evaluate(Object arg0) {
				final String[] pieces = (String[]) arg0;
				for (int i=0; i < pieces.length; i++) {
					final String piece = pieces[i];
					if (StringUtils.contains(piece, MSIE_ID)) {
						return true;
					}
				}
				return false;
			}
        });
        
    	// if it's not msie but test for Opera because it can identify itself as msie...
        if (browserParticulars == null) {
        	// get the position of the last browser key found
        	int count = matches.size() - 1;
        	browserParticulars = (String[])matches.get(count);
        }
        
        longName = browserParticulars[0];
        browserName = browserParticulars[1];
        
        // get browserName from string
        Matcher nameMatcher = NameOnlyPat.matcher(browserName);
        if (nameMatcher.matches()) {
        	browserName = nameMatcher.group(nameMatcher.groupCount());
        }
        
        majorVersion = browserParticulars[2];
        
        // parse the minor version string and look for alpha chars
        if (browserParticulars[3] != null) {
            // eg: .7b
            // [0] = .7b
            // [1] = .7
            // [2] = b
            matches = getMatches(MinorVersionPat, browserParticulars[3], 3);
            if (matches.isEmpty())
                return;

            int count = matches.size() - 1;
            browserParticulars = (String[])matches.get(count);
            if (browserParticulars[1] != null)
                minorVersion = browserParticulars[1];
            else
                minorVersion = ".0";
            
            if (PunctuationOnlyPat.matcher(minorVersion).matches())
            	minorVersion = StringUtils.EMPTY;

            if (browserParticulars[2] != null && !PunctuationOnlyPat.matcher(browserParticulars[2]).matches())
                revisionVersion = browserParticulars[2];
        }
    }
    
    private ArrayList getMatches(Pattern pat, String str, int countGroups) {
        Matcher matcher = pat.matcher(str);
        ArrayList matches = new ArrayList();
        try {
            ArrayList groups = new ArrayList();
            while (matcher.find()) {
                groups.clear();
                int nullCount = 0;
                for (int i=0; i<countGroups; i++) {
                    int start = matcher.start(i);
                    int end = matcher.end(i);
                    if (start >= 0 && end >= 0) {
                        String sub = str.substring(start, end);
                        if (StringUtils.isNotEmpty(sub))
                            groups.add(sub);
                        else {
                            groups.add(null);
                            nullCount++;
                        }

                    }
                    else {
                        groups.add(null);
                        nullCount++;
                    }
                }
                if (groups.size() > 0 && nullCount != groups.size())
                    matches.add(groups.toArray(new String[groups.size()]));
            }
        }
        catch (Exception e) {
            log.error(e);
        }
        
        return matches;
    }
}
