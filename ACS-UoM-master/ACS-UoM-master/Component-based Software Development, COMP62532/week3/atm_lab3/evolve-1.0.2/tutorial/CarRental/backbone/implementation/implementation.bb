stratum implementation
    parent backbone
    
    depends-on backbone-profile, api
{
    component Creator implementation-class com.intrinsarc.backbone.runtime.implementation.Creator
    {
        attributes:
            factoryNumber: int;
        ports:
            create provides ICreate;
    }

    component FactoryBase is-factory
    {
        ports:
            creator is-create-port provides ICreate;
    }

    component State implementation-class com.intrinsarc.backbone.runtime.implementation.State
    {
        «state» 
        ports:
            in provides ITransition,
            out[0 upto 1] requires ITransition,
            events force-bean-main provides IEvent;
    }

    component Start is-normal implementation-class com.intrinsarc.backbone.runtime.implementation.Terminal
         resembles State
    {
        «state» 
        delete-ports:
            events;
        ports:
            startTerminal force-bean-main provides ITerminal;
        replace-ports:
            in becomes replaced-in provides ITransition;
    }

    component End is-normal implementation-class com.intrinsarc.backbone.runtime.implementation.Terminal
         resembles State
    {
        «state» 
        delete-ports:
            events;
        ports:
            endTerminal force-bean-main provides ITerminal;
        replace-ports:
            in becomes replaced-ina provides ITransition;
    }

    component CompositeState is-normal
         resembles State
    {
        «state» 
        parts:
            start: Start,
            end: End;
    }

    component StateDispatcher implementation-class com.intrinsarc.backbone.runtime.implementation.StateDispatcher
    {
        ports:
            dEvents force-not-bean-main wants-required-when-providing provides IEvent,
            dDispatch[0 upto *] requires IEvent,
            dStart requires ITerminal,
            dEnd[0 upto *] requires ITerminal;
        port-links:
            dEvents-dDispatch joins dEvents to dDispatch;
    }

}

