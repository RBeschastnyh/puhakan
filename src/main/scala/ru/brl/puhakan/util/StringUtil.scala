package ru.brl.puhakan.util

/** String extensions.
 * 
 * @author RBeschastnykh
 * @since 0.0.1 
 */
class StringUtil {
  
}

/** Extension for working with lines in phk.yml config files.
 * 
 * @author RBeschastnyh
 * @since 0.0.1        
 */
extension (x: String) {
  def countLeadingSpaces: Int = x.length - x.stripLeading().length
}
