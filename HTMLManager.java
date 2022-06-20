//Author: Robert
//Program that uses stacks and queues to fix html tags

import java.util.*;

public class HTMLManager {

     private Queue<HTMLTag> tags;
     
     public HTMLManager(Queue<HTMLTag> html) {
     
        if(html == null) {
        
            throw new IllegalArgumentException();
            
        } else {
        
            tags = html;
            
        }
     }
     //pre: none
     //post: return tags
     public Queue<HTMLTag> getTags() {
     
         return tags;
     }
     
     //pre: none
     //post: turns the given text file into a trimmed string
     public String toString() {
         String str = "";
         
         Iterator<HTMLTag> itr = tags.iterator();
         while(itr.hasNext()) {
            str += itr.next().toString().trim();
         }
         
         return str;
     }
     
     //pre: none
     //post: fixes unclosed html tags from given text file
     public void fixHTML() {
     
         Stack<HTMLTag> stack = new Stack<>();
         Queue<HTMLTag> fixedTags = new LinkedList<HTMLTag>();
         Iterator<HTMLTag> itr = tags.iterator();
         
         while(itr.hasNext()){
            
           HTMLTag cur = itr.next();
          
  
           if (cur.isOpening()) {
           
               stack.push(cur);
               fixedTags.add(cur);
           }
           if (cur.isClosing()) {

               if (!stack.isEmpty()) {
                    
                   if (stack.peek().matches(cur)) {
                     fixedTags.add(cur);
                     stack.pop();
                   }
                   else {
                     fixedTags.add(stack.peek().getMatching());
                     stack.pop();
                   }
               }
           }

           if (cur.isSelfClosing()) {
           
               fixedTags.add(cur);
           }
          
       }
       
       //If there are still opening tags in the stack, add the matching closing tags to the queue
       if(!stack.isEmpty()){
         Iterator<HTMLTag> itr2 = stack.iterator();
         while(itr2.hasNext()){
            fixedTags.add(itr2.next().getMatching());
            itr2.remove();
         }
       }
       tags = fixedTags;
    }
    
}
/*
  ----jGRASP exec: java -ea HTMLChecker
 ===============================
 Processing tests/test1.html...
 ===============================
 HTML: <b><i><br /></b></i>
 Checking HTML for errors...
 HTML after fix: <b><i><br /></i></b>
 ----> Result matches Expected Output!
 
 ===============================
 Processing tests/test2.html...
 ===============================
 HTML: <a><a><a></a>
 Checking HTML for errors...
 HTML after fix: <a><a><a></a></a></a>
 ----> Result matches Expected Output!
 
 ===============================
 Processing tests/test3.html...
 ===============================
 HTML: <br /></p></p>
 Checking HTML for errors...
 HTML after fix: <br />
 ----> Result matches Expected Output!
 
 ===============================
 Processing tests/test4.html...
 ===============================
 HTML: <div><div><ul><li></li><li></li><li></ul></div>
 Checking HTML for errors...
 HTML after fix: <div><div><ul><li></li><li></li><li></li></ul></div></div>
 ----> Result matches Expected Output!
 
 ===============================
 Processing tests/test5.html...
 ===============================
 HTML: <div><h1></h1><div><img /><p><br /><br /><br /></div></div></table>
 Checking HTML for errors...
 HTML after fix: <div><h1></h1><div><img /><p><br /><br /><br /></p></div></div>
 ----> Result matches Expected Output!
 
 ===============================
         All tests passed!
 ===============================
 
  ----jGRASP: operation complete.
 */
         
