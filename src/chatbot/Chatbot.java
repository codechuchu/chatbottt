package chatbot;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Chatbot extends JFrame implements ActionListener
{
    static Map<String, List<String>> answers= new HashMap<>();
    static Map<String,Integer> months= new HashMap<>();
    static boolean possibleDateFound;
    static int currentMonth;
    static int day=0;
    static boolean zodiacDetected;
    static String message="Welcome to Your Daily Horoscope Do you know your zodiac sign? If so, just type it below or enter your birth date and month in this \nformat (ie) September 17";
    static String suggest="You can also check other Horoscope readings by providing their zodiacs or providing us a new birthdate.";
    static JTextArea area=new JTextArea();
    static JTextField field=new JTextField();
    static String currentZodiac;
    JScrollPane sp;
    JButton send;

    public Chatbot(String title)
    {
        super(title);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(Color.cyan);
        field=new JTextField();
        send=new JButton("Send");
        send.setFont(new Font("serif", Font.PLAIN,16));
        send.setBackground(Color.white);
        send.setBounds(790,555,70,35);
        add(send);
        //For Text area
        area.setEditable(false);
        area.setBackground(Color.white);
        area.setLineWrap(true);
        area.setMargin(new Insets(0,10,10,10));
        add(area);
        area.setFont(new Font("Serif",Font.PLAIN,20));
        //scrollbar
        sp=new JScrollPane(area,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setBounds(10,77  ,850,470);
        add(sp);

        //For TextField
        field.setSize(780,35);
        field.setLocation(10,555);
        field.setForeground(Color.black);
        field.setFont(new Font("Serif",Font.PLAIN,20));
        add(field);
        bot(message);
        send.addActionListener(this);
        getRootPane().setDefaultButton(send);
            
        JPanel jp = new JPanel();
        jp.setBackground(new Color(102,178,255));
        jp.setBounds(10, 0, 850, 76);
        jp.setLayout(null);
        add(jp);
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel  back = new JLabel(i3);
        back.setBounds(10, 23, 30, 30);
        jp.add(back);
        back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                System.exit(0);
            }
        });
        
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/dream.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel  img = new JLabel(i6);
        img.setBounds(45, 15, 50, 50);
        jp.add(img);
        
        JLabel name  = new JLabel("Yume");
                name.setBounds(110, 15, 120, 25);
                name.setForeground(Color.WHITE);
                name.setFont(new Font("SAN_SERIF",Font.BOLD,19));
                jp.add(name);
                
        JLabel status = new JLabel("Active Now");
                status.setBounds(110, 35, 120, 25);
                status.setForeground(Color.WHITE);
                status.setFont(new Font("SAN_SERIF",Font.BOLD,16));
                jp.add(status);

    }
    public void actionPerformed(ActionEvent e)
    {
        area.append("User: "+field.getText());
        know();
        field.setText("");

    }

    public static void know(){
        String userTypedKeywords= field.getText().toLowerCase();

        char[] chars= userTypedKeywords.toCharArray();
        for(char c: chars){
            if (Character.isDigit(c)) {
                possibleDateFound=true;
                zodiacDetected=false;
            }
        }

        if(answers.containsKey(userTypedKeywords)){
            currentZodiac=userTypedKeywords;
            zodiacDetected=false;
        }
        if(currentZodiac==null || possibleDateFound) {
            if (currentMonth==0 || possibleDateFound) {
                String[] temp= userTypedKeywords.split(" ");
                for(String str: temp){
                    if(months.containsKey(str)){
                        currentMonth=months.get(str);
                    }
                }
            }
            if(day==0 || possibleDateFound){
                String temp= userTypedKeywords.replaceAll("[^0-9]"," ");
                temp = temp.trim();
                message=temp;
                if(!temp.equals("")) {
                    day = Integer.parseInt(temp);
                }
                if(currentMonth!=0 && day!=0){
                    ZodiacSign(currentMonth,day);
                    possibleDateFound=false;
                }
            }

        }
        if(currentZodiac!=null && !zodiacDetected){
            message="We have readings for "+ currentZodiac+ "'s health, wealth , love and relationship, please chose any or all";
            zodiacDetected= true;
        }
        if(userTypedKeywords.contains("all")){
            message= "\n\t-"+currentZodiac+" health-\n"+ answers.get(currentZodiac).get(0) + answers.get(currentZodiac).get(1) +"\n\t-"+currentZodiac+" wealth-\n"+answers.get(currentZodiac).get(2)
                    +"\n"+suggest;
        }
        if(userTypedKeywords.contains("health")){
            message="\n\t-"+currentZodiac+" health-\n"+ answers.get(currentZodiac).get(0);
        }
        if(userTypedKeywords.contains("wealth")){
            message= "\n\t-"+currentZodiac+" wealth-\n"+answers.get(currentZodiac).get(2);
        }
        if(userTypedKeywords.contains("love")|| userTypedKeywords.contains("relationship")){
            message= answers.get(currentZodiac).get(1);
        }
        if(currentZodiac==null){
            message="sorry can you please tell us your zodiac sign or your birth date?";
        }
        bot(message);
        message="";
    }
    public static void ZodiacSign(int month, int day){
        if ((month == 1 && day >= 20) || (month == 2 && day <= 18)) {
            currentZodiac= "aquarius";
        } else if ((month == 2 && day >= 19) || (month == 3 && day <= 20)) {
            currentZodiac= "aquarius";
        } else if ((month == 3 && day >= 21) || (month == 4 && day <= 19)) {
            currentZodiac ="aries";
        } else if ((month == 4 && day >= 20) || (month == 5 && day <= 20)) {
            currentZodiac= "taurus";
        } else if ((month == 5 && day >= 21) || (month == 6 && day <= 20)) {
            currentZodiac= "gemini";
        } else if ((month == 6 && day >= 21) || (month == 7 && day <= 22)) {
            currentZodiac= "cancer";
        } else if ((month == 7 && day >= 23) || (month == 8 && day <= 22)) {
            currentZodiac= "leo";
        } else if ((month == 8 && day >= 23) || (month == 9 && day <= 22)) {
            currentZodiac= "virgo";
        } else if ((month == 9 && day >= 23) || (month == 10 && day <= 22)) {
            currentZodiac= "libra";
        } else if ((month == 10 && day >= 23) || (month == 11 && day <= 21)) {
            currentZodiac= "scorpio";
        } else if ((month == 11 && day >= 22) || (month == 12 && day <= 21)) {
            currentZodiac= "sagittarius";
        } else {
            currentZodiac= "capricorn";
        }
    }
    public static void bot(String message)
    {
        area.append("\nYume: "+message+"\n");
    }
    public static void main(String[] args)
    {
        String aries_health= "They are known for being impulsive and headstrong. While these qualities can be positive, they can \nalso lead to health problems. They are more likely to suffer from stress and anxiety than other signs, \nas well as headaches and migraines. They are also prone to injuries due to their impulsive nature.\nThey should make sure to get adequate rest, exercise regularly, and practice stress management \ntechniques and avoid stimulants such as caffeine.";
        String taurus_health= "While they are typically healthy people, there are some health problems that they are more prone \nto than others. These include physical health problems like back pain and headaches as well as those \nrelated to throat and mouth. Remember, your health is in your hands! It’s important for them to take \ntime to connect with nature. Getting outside and taking in the beauty of the natural world can\nbe a great way to relax and restore your mind and body.";
        String gemini_health= "They are known for being talkative and always on the go, which can put stress on their nervous \nsystem. Try to take breaks and practice relaxation techniques like yoga or meditation. They are also \nsusceptible to colds and respiratory problems. They may have a tendency to skip meals or eat on the \nrun. Eating nutritious meals and staying hydrated will help keep them energized and focused. \nOverthinking can lead to anxiety.";
        String cancer_health= "They are known for their sensitivity, which can make them susceptible to stress and anxiety. \nCommon health problems for them include digestive issues, headaches, and fatigue. They should focus on getting plenty of rest, eating a balanced diet, and engaging in regular exercise. Finding ways to \nmanage stress is also key, as this can help them stay healthy and reduce the risk of developing \nhealth problems.";
        String leo_health= "They are prone to heart problems. They may have high blood pressure or suffer from a cardiac-\nrelated issues. They need to take care of their heart and watch their cholesterol levels. They should \nalso exercise regularly and eat a healthy diet. Some health tips include getting plenty of sleep, avoiding\ntoo much sodium and sugar, and maintaining an active lifestyle. Additionally, they should \nmonitor their blood pressure levels regularly.";
        String virgo_health="While they are generally healthy, there are some health problems that you are more prone to than \nothers. One common problem is indigestion. They tend to worry a lot, which can lead to stomach \nproblems. They need to take care of their stomach by eating smaller meals more often. They often \nget tense and stressed,which can lead to headaches. They should make sure to drink plenty of water \nand get enough sleep.";
        String libra_health="They love different food cuisines and are known for their sweet tooth. They can have difficulty \nsaying no when they should, leading to unhealthy people-pleasing behaviours. They are prone to skin \nproblems like acne, rashes, and other skin irritations. To stay healthy, they should work on balancing \ntheir lives, setting healthy boundaries with others, and taking time to relax and unwind. Proper \nnutrition and hydration will help keep the body energized";
        String scorpio_health="They are known for being intense and passionate people. They may suffer from heartburn, indigestion, \nor other gastrointestinal issues from time to time. As they tend to be very active and energetic, \nthey sometimes put strain on their respiratory systems. This can lead to problems like asthma or \nbronchitis. They often deal with a lot of stress in their lives. This can manifest itself in physical health \nproblems like high blood pressure or headaches.";
        String sagittarius_health="This is a fire sign, and as such, they are prone to problems with their blood pressure and heart.\nThey are also susceptible to gastrointestinal issues, such as ulcers and heartburn. They often have \na fast-paced lifestyle which can place strain on the nervous system. Problems with the lower \nabdomen are also common. They should be sure to eat a healthy diet and exercise regularly to \nmaintain their health and practice good self-care.";
        String capricorn_health="They can be healthy and fit, but are also prone to certain health problems. For instance, they \nare more likely to suffer from depression. Joint and bone problems, such as arthritis and osteoporosis, \nare also common for them. They also face issues with skin and hair. A healthy diet that includes plenty of fruits and vegetables, whole grains, and lean protein can help maintain good health and \nenergy levels.";
        String aquarius_health="They are known for their energetic and adventurous nature, so incorporating physical activity \ninto their daily routine is important. This could include yoga, running, cycling, or hiking. They are of \nadventurous nature, so incorporating a variety of healthy foods into their diet is important. They are \nalso said to be prone to anxiety and have a higher-than-average risk of developing schizophrenia.\nIt's important for them to stay hydrated.";
        String pisces_health="Pisces: They are highly sensitive and prone to anxiety and depress";

        String aries_love="Aries love a healthy tug-of-war with their partners, but not every single disagreement calls for a full-\nscale duel. If you’re ultimately fighting only for the sake of fighting, you’ll lose either way.";
        String taurus_love="A romantic Taurus will always let their partner know they love them. But watch out for the balance \nbetween sweet gestures and smothering attention. Trust that if you give your partner space to miss \nyou ,they will";
        String gemini_love="Geminis can easily lose themselves in constant conversation with their partner. But even the most \ninteresting person in the world runs out of steam eventually. To keep it fresh, take a break to\npursue your solo interests. It’ll give you more to talk about.";
        String cancer_love="Cancer scrave security in all they do, including matters of the heart. But learning to trust that your \npartner is really there for you (yes, really) will allow you to actually receive their sweet\nlove in a way that nourishes you both. You just have to open your shell, crab.";
        String leo_love="You’re the CEO of your own life, Leo, but in a relationship, practice playing second fiddle every now and then to your equally wonderful partner. They need to know that you see their shine as much as\nthey see yours.";
        String virgo_love="Virgos are known for being hard on themselves, but they are often second-hardest on their partners \n(only because you care, of course). If you want your partner to know how much you really love them,\nlearn to accept their flaws at least the little ones.";
        String libra_love="Don’t let your need to be liked override your real desires and needs. If you’re in the mood for pizza, \ndon’t play the “I could do anything for dinner” game. Your partner won’t love you less for \nasserting yourself they will love you more.";
        String scorpio_love="Your memory is long, Scorpio, and you’ve got terabytes of emotional metadata on who and how \nanyone has ever crossed you. But in a relationship (one that you intend to continue!), it’s worth it to \nlet go of the little scrapes and scuffles.";
        String sagittarius_love="Sagittarians are honest to a fault and that fault, specifically, is that they rarely sugarcoat even \ntheir harshest opinions. Being truthful is key to any good partnership, but the medium is the \nmessage, and what you say is just as important as how you say it.";
        String capricorn_love="The great thing about Capricorns is that they take commitment seriously very seriously. But sometimes \nit’s necessary to ease up on the work part of relationships and enjoy the pure joy of it. Don’t\nworry you’re not less of an adult if you let yourself be a fool for love once in a while.";
        String aquarius_love="As the sign of the genius, Aquarians can be a handful when they think they are right about something.\nIn love, however, you have to be open to your partner’s point of view. After all, if they weren’t\na genius, too, would you be with them?";
        String pisces_love="Pisces can be a tad self-sacrificing for the ones they love. But you may surprise yourself (and certainly\nyour partner!) with pent-up resentment if you give in too often and never put your foot down.\nRelationships are all about give and take, and sometimes you need to take a little more.";
  
        String aries_wealth="As the first of the horoscopes, an Aries is happy in a job where they can be the boss! Like all fire signs, Aries have a lot of energy and are often the most successful in a position that they love. So pick your passion, Aries, and go for it! You have the drive and motivation to be a successful boss as long as you love what you do. \n" +
                "Our ideas for you: advertising, broadcasting, CEO of literally anything";
        String taurus_wealth="The strong bull sign has the ability to trudge forward through anything, especially difficult situations. \nTaureans should consider opening a financial-centered business, where they can take time to organize \nand handle other’s documents and finances. Tauruses are also notorious food lovers, so you may find \njoy in opening a high-end restaurant where you can handle the back end of the business!\n" +
                "Our ideas for you: accountant agency, finance manager, multi-business owner";
        String gemini_wealth="Geminis are excellent communicators, and great at social networking! Geminis also love to discuss \ntheir ideas and learn from others, so working within the field of communication is a given for this \nsign. While a Gemini would be successful starting a PR or marketing firm, you can also find them \ngiving classes or on stage talking about their successes and teaching others how they did it!\n" +
                "Our ideas for you: social media agency, travel agency, retail buying";
        String cancer_wealth="Our sensitive seeds, a Cancer is empathetic by nature. Cancer’s have the innate ability to sense what \nother people need and want; traits that are often overlooked! However, Cancer’s too often put their \nentire heart and soul into projects, and feel like an insult to their career is a personal attack on \nthemselves as well. For this reason, a Cancer would be great setting up an organization that helps \nothers, from wellness retreats to ONGs so that they can nurture others with their caring souls.\n" +
                "Our ideas for you: human resources agency, non-profit, law firm";
        String leo_wealth="“Subtle” is not a term that Leos are familiar with. Leo’s love to take center stage, so people born under this sign are likely to want to start a business where they can shine and show off their outgoing personality. If you’re a Leo, consider opening up an entertainment-centered business, where you can truly \nexpress yourself.\n" +
                "Our ideas for you: real estate, tourism, fashion";
        String virgo_wealth="Our token perfectionists, Virgos will run their business like a well-oiled machine. There will be no \nmistakes, no forgotten orders, no mistakes on a spreadsheet in a business owned by a Virgo! The \nForbes Billionaires list has more Virgos than any other sign, and it’s due to their nature to be \nobsessive concerning attention to detail. Virgo, you’d be great in technology, logistics, or any agency \nwork where you can make it big. And don’t forget us when you make that Forbes list!\n" +
                "Our ideas for you: statistics, translation, tech";
        String libra_wealth="Libras are our luxe ladies! Libras love beauty in all forms and are partial to live’s more luxurious \nthings. They’re also super diplomats, and hate causing conflict, so you’ll find a Libra willing to bend \nover backward for their client. This sign would be a great owner of a luxury design business, or even \nowning a chic hotel chain. \n" +
                "Our ideas for you: supervisory agency, diplomatic organization, marketing";
        String scorpio_wealth="The sign everyone roasts on social media, Scorpios are known for their stinging personality. They are \nthe scorpion, after all. You’re likely to find a Scorpio setting up a business that can show off the more \nsassy and confident parts of their personality. The next Victoria’s Secret, anyone?\n"+
                "Our ideas for you: PR, entertainment, consulting";
        String sagittarius_wealth="Sagittarius is meant to be a digital nomad! One of the most independent signs, Sagittarius are born with the travel bug. Sagittarius are also one of the luckiest signs; ask your Sagittarius friend when they’re \nnext investing in crypto, because they’re the most likely to be around for the next boom!\n" +
                "Our ideas for you: coaching, animal training, outdoors";
        String capricorn_wealth="Capricorns on a mission are a force to be reckoned with. Capricorns are hard-working and ambitious, and usually, seek out money-making jobs. Dedicated to blazing a new path, Capricorns usually have a hard time working on teams, especially ones that they aren’t leading. With incredible organizational \nskills, Capricorns are excellent in IT or business positions, where they’re paid the big bucks for their \norganization. \n" +
                "Our ideas for you: IT, people management, science-related organizations";
        String aquarius_wealth="Aquarians desire freedom and self-expression above all else, and would become great entrepreneurs. Those born under the Aquarius sign often seek to answer life’s big questions (“Why am I here? What is my purpose?”) and don’t like to fit the typical mold. As natural humanitarians, Aquarians would be \nexcellent at creating a start-up that combines a variety of interests, such as the natural or holistic \nworld along with a vision for the future. \n" +
                "Our ideas for you: organic farming, navigation, inventing";
        String pisces_wealth="You’re naturally intuitive, but also creative & prone to following your heart. Pisces are great at \nanswering other people’s needs and figuring out the root of problems, so it’s likely that they’ll be \ndrawn to anything that helps other people. Pisces may feel unfulfilled in a traditional, 9-5 office role. A super creative Pisces may be drawn to art or therapy-based work, while a business interest Pisces \nwould be a unique entrepreneur. \n" +
                "Our ideas for you: physical therapy or psychological healing, art, design";

        answers.put("aries",Arrays.asList(aries_health,"\n\t-Aries love: Pick Your Battles- \n"+aries_love,aries_wealth));
        answers.put("taurus",Arrays.asList(taurus_health,"\n\t-Taurus love: Let It Breathe-\n"+taurus_love,taurus_wealth));
        answers.put("gemini",Arrays.asList(gemini_health,"\n\t-Gemini's love: Nurture Your Own Interests-\n"+gemini_love,gemini_wealth));
        answers.put("cancer",Arrays.asList(cancer_health,"\n\t-Cancer's love: Trust Your Partner\n"+cancer_love,cancer_wealth));
        answers.put("leo",Arrays.asList(leo_health,"\n\t-Leo's love: Let Them Shine-\n"+leo_love,leo_wealth));
        answers.put("virgo",Arrays.asList(virgo_health,"\n\t-Virgo's love: Accept the Flaws\n"+virgo_love,virgo_wealth));
        answers.put("libra",Arrays.asList(libra_health,"\n\t-Libra's love: Be Yourself-\n"+libra_love,libra_wealth));
        answers.put("scorpio",Arrays.asList(scorpio_health,"\n\t-Scorpio's love: Lose the Grudges-\n"+scorpio_love,scorpio_wealth));
        answers.put("sagittarius",Arrays.asList(sagittarius_health,"\n\t-Sagittarius love: Watch That Tone\n"+sagittarius_love,sagittarius_wealth));
        answers.put("capricorn",Arrays.asList(capricorn_health,"\n\t-Capricorn's love: Lighten Up-\n"+capricorn_love,capricorn_wealth));
        answers.put("aquarius",Arrays.asList(aquarius_health,"\n\t-Aquarius love: Lower the Know-It-All Volume-\n"+aquarius_love,aquarius_wealth));
        answers.put("pisces",Arrays.asList(pisces_health,"\n\t-Pisces love: Don't be a hero-\n"+pisces_love,pisces_wealth));

        months.put("january",1);
        months.put("february",2);
        months.put("march",3);
        months.put("april",4);
        months.put("may",5);
        months.put("june",6);
        months.put("july",7);
        months.put("august",8);
        months.put("september",9);
        months.put("october",10);
        months.put("november",11);
        months.put("december",12);
        Chatbot cb=new Chatbot("Dream");
        cb.setSize(885,640);
        cb.setLocation(545,250);

    }

    }

