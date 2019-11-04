stratum backbone-profile
    parent backbone
    
{
    component type is-primitive implementation-class Type
    {
    }

    component boolean is-primitive implementation-class java.lang.Boolean
    {
    }

    component Color is-primitive implementation-class java.awt.Color
    {
    }

    component int is-primitive implementation-class java.lang.Integer
    {
    }

    component byte is-primitive implementation-class java.lang.Byte
    {
    }

    component short is-primitive implementation-class java.lang.Short
    {
    }

    component char is-primitive implementation-class java.lang.Char
    {
    }

    component long is-primitive implementation-class java.lang.Long
    {
    }

    component float is-primitive implementation-class java.lang.Float
    {
    }

    component double is-primitive implementation-class java.lang.Double
    {
    }

    component String is-primitive implementation-class java.lang.String
    {
    }

    component Date is-primitive implementation-class java.util.Date
    {
    }

    component Object is-primitive implementation-class java.lang.Object
    {
    }

    component element is-stereotype implementation-class element
    {
        attributes:
            force-implementation: String,
            legacy-bean: boolean,
            no-inheritance: boolean;
    }

    component interface is-stereotype implementation-class interface
         resembles element
    {
    }

    component component is-stereotype implementation-class component
         resembles element
    {
        attributes:
            cluster: boolean,
            factory: boolean,
            lifecycle-callbacks: boolean,
            navigable: boolean,
            placeholder: boolean,
            protocols: String;
    }

    component primitive-type is-stereotype implementation-class primitive-type
         resembles element
    {
    }

    component stratum is-stereotype implementation-class stratum
    {
        attributes:
            bb-classpath: String,
            bb-cmd-line-arguments: String,
            bb-composite-package: String,
            bb-java-folder: String,
            bb-java-suppress: boolean,
            bb-run-component: String,
            bb-run-port: String,
            bb-run-stratum: String,
            bb-source-folder: String,
            bb-source-suppress: boolean,
            check-once-if-read-only: boolean,
            destructive: boolean,
            export-info: String,
            export-version: String,
            generation-profile: String,
            java-package: String,
            preamble: String,
            relaxed: boolean;
    }

    component connector is-stereotype implementation-class connector
    {
        attributes:
            directed: boolean;
    }

    component port is-stereotype implementation-class port
    {
        attributes:
            bean-main: boolean,
            bean-no-name: boolean,
            force-not-bean-main: boolean,
            no-generation-port: boolean,
            wants-required-when-providing: boolean;
    }

    component attribute is-stereotype implementation-class attribute
    {
        attributes:
            actual-value: String,
            no-generation: boolean;
    }

    component slot is-stereotype implementation-class slot
    {
        attributes:
            actual-slot-value: String;
    }

    component visual-effect is-stereotype implementation-class visual-effect
    {
    }

    component hide is-stereotype implementation-class hide
         resembles visual-effect
    {
    }

    component backbone-delta is-stereotype implementation-class backbone-delta
    {
    }

    component backbone-overriden-slot is-stereotype implementation-class backbone-overriden-slot
    {
        attributes:
            overriddenSlotAlias: boolean,
            overriddenSlotText: String;
    }

    component state-part is-stereotype implementation-class state-part
         resembles visual-effect
    {
    }

    component trace is-stereotype implementation-class trace
    {
    }

    component state is-stereotype implementation-class state
         resembles component
    {
    }

}

