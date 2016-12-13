package com.github.idosu.cli.cli.tests;

import com.github.idosu.cli.Arg;
import com.github.idosu.cli.Cli;
import com.github.idosu.cli.EndArg;
import com.github.idosu.cli.Name;
import org.junit.Test;

/**
 * TODO: desc
 *
 * @author
 *      <br>12 Dec 2016 idosu
 */
public class GeneralTest {
    // Usage: ${CliConfig#name} [ options ] ${Ending#directory}
    //  ${CliConfig#description}

    // Options:
    //  ${Arg#name}    ${Arg#description}
    /**
     * Arg.name() ? longOrShort: short(=1 char) -n NAME long(>1 chars) --name=NAME (NAME is Name.valueString)
     * examples:
     * -m MODE, --mode=MODE    set file mode (as in chmod), not a=rwx - umask
     */
//    @CliConfig(name="mkdir", description="Create the DIRECTORY(ies), if they do not already exist.")
    public class mkdirConfig {
        // '-m a=rwx' '-ma=rwx' '--mode=a=rwx'
        @Arg(description="set file mode (as in chmod), not a=rwx - umask")
        public String mode;

        // '-p' '--parents'
        @Arg(description="no error if existing, make parent directories as needed")
        public boolean parents = false;

        // '-v' '--verbose'
        @Arg(description="print a message for each created directory")
        public boolean verbose = false;

        // '-Z'
        @Arg(name=@Name("Z"), description="set SELinux security context of each created directory to the default type")
        public boolean selinux = false;

        @EndArg(description="")
        public String[] directory;
    }

    public class mkdirSimpleConfig {
        public String mode;
        public boolean parents = false;
        public boolean verbose = false;
        public boolean selinux = false;

        @EndArg
        public String[] directory;
    }

    @Test
    public void test() {
        new Cli<>(mkdirConfig.class);
    }

    @Test
    public void test2() {
        new Cli<>(mkdirSimpleConfig.class);
    }

    /*public void foo() {
        String[] args = { };

        mkdirConfig config;
        Cli<mkdirConfig> cli;

        // 1 - plain ol' java - for not it is the only api - TODO
        cli = Cli.forClass(mkdirConfig.class);
        try {
            config = cli.parse(args);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.err.println(cli);
            System.exit(1);
            return;
        }

    }*/
}
