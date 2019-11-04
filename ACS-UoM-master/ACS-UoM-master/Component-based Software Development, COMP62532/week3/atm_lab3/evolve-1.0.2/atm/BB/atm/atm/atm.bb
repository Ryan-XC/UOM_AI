stratum 9363e1b0-92bc-4084-ab9d-4c251a8a662d/ATM/
    parent 10a0d790-5479-4ff4-9b68-e3d98f2316cc/model/
     is-relaxed
    depends-on backbone
{
    interface 498d6e81-dead-45ae-acf7-18d4fb1e5aef/IBankServices/ implementation-class uk.man.atm.IBankServices
    {
    }

    interface 4d4c0efa-7bd0-4b9c-9b27-0378285e1e11/IAtmCli/ implementation-class uk.man.atm.IAtmCli
    {
    }

    interface 2718928d-3c07-48f0-8a1c-6d3fa279dc5b/ICardReader/ implementation-class uk.man.atm.ICardReader
    {
    }

    interface 17c2fcba-87b8-4e96-b129-52c85893aab1/IAccount/ implementation-class uk.man.atm.IAccount
    {
    }

    interface 3338bbb5-7de1-4c0b-82a1-e424f3cd2cf3/IRun/ implementation-class uk.man.atm.IRun
    {
    }

    component 8f468b46-543f-4acd-affc-b0bc63e6358d/CardReader/ implementation-class uk.man.atm.CardReader
    {
        ports:
            9c612239-f661-4184-8c3b-c2f97e89e447/account/ provides 2718928d-3c07-48f0-8a1c-6d3fa279dc5b/ICardReader/;
    }

    component 581099f2-7bf8-491e-88e9-e76a8210415b/Integer/
    {
    }

    component 2a6bb532-bfd1-4c54-849d-f8f7020a68b2/Bank/ implementation-class uk.man.atm.Bank
    {
        ports:
            5859fc06-b4f5-4343-b7c6-518871d59990/services/ provides 498d6e81-dead-45ae-acf7-18d4fb1e5aef/IBankServices/;
    }

    component 81c54717-8e57-4d66-bf20-4205843f05b2/Account/
    {
    }

    component 3f2855b4-8d39-4a14-86d2-7d3eb642c51d/ATMSystem/
    {
        ports:
            192e2cc0-af3d-4ccd-974c-dc9470b7faaa/cli/;
        parts:
            e1ef5b7d-e9af-4f41-8cf1-64da4729d190: 8f468b46-543f-4acd-affc-b0bc63e6358d/CardReader/,
            a7f2b880-6677-4420-8c06-9275ab06ac43: 2a6bb532-bfd1-4c54-849d-f8f7020a68b2/Bank/,
            3d0ba4f8-7297-498e-ac86-dc4a7bb27a39: 3b31ad5e-a7f8-4faa-898a-761fcdecd076/UI/;
        connectors:
            cb16e691-0df5-427c-a533-836f3b9c9998 joins 4275c4ba-5d15-45bb-9bba-764a547a19b1/bank/@3d0ba4f8-7297-498e-ac86-dc4a7bb27a39 to 5859fc06-b4f5-4343-b7c6-518871d59990/services/@a7f2b880-6677-4420-8c06-9275ab06ac43,
            0bb6c907-f195-44c4-9c32-d7f76d460e16 joins 9b7c454a-5f40-401b-9c67-5904494e2bf9/reader/@3d0ba4f8-7297-498e-ac86-dc4a7bb27a39 to 9c612239-f661-4184-8c3b-c2f97e89e447/account/@e1ef5b7d-e9af-4f41-8cf1-64da4729d190,
            c9e946f5-16d4-4eda-8a68-7f67796d2be3 joins f1ce23b7-a29b-4aa1-b76f-fef948ba3065/display/@3d0ba4f8-7297-498e-ac86-dc4a7bb27a39 to 192e2cc0-af3d-4ccd-974c-dc9470b7faaa/cli/;
    }

    component 3b31ad5e-a7f8-4faa-898a-761fcdecd076/UI/ implementation-class uk.man.atm.UI
    {
        ports:
            4275c4ba-5d15-45bb-9bba-764a547a19b1/bank/ requires 498d6e81-dead-45ae-acf7-18d4fb1e5aef/IBankServices/,
            9b7c454a-5f40-401b-9c67-5904494e2bf9/reader/ requires 2718928d-3c07-48f0-8a1c-6d3fa279dc5b/ICardReader/,
            f1ce23b7-a29b-4aa1-b76f-fef948ba3065/display/ provides 4d4c0efa-7bd0-4b9c-9b27-0378285e1e11/IAtmCli/;
    }

    component 95f518c5-1826-4745-bf1e-44b7efd3a6cc/ATMExample/
    {
        ports:
            bf2e4773-d1a9-4e95-8f1c-c16c850b9928/run/;
        parts:
            542cf746-48e0-402b-8a98-8ae6ee5483c6: 3f2855b4-8d39-4a14-86d2-7d3eb642c51d/ATMSystem/,
            0f5085ce-5691-4f40-90a1-d667e4e9b235: d34ecc37-6902-41cd-99c2-fbbe7555d1ca/ATMRunner/;
        connectors:
            845604f8-d66b-4dd2-9dbf-ae509720a0ae delegates-from 5fd251e1-ab35-4bde-b1ce-71ab65a9eb39/run/@0f5085ce-5691-4f40-90a1-d667e4e9b235 to bf2e4773-d1a9-4e95-8f1c-c16c850b9928/run/,
            2e82810d-897c-4c89-b181-efd9e1a10ddf joins bc2581d5-939a-492f-a00f-8a24640ea72e/atmCli/@0f5085ce-5691-4f40-90a1-d667e4e9b235 to 192e2cc0-af3d-4ccd-974c-dc9470b7faaa/cli/@542cf746-48e0-402b-8a98-8ae6ee5483c6;
    }

    component d34ecc37-6902-41cd-99c2-fbbe7555d1ca/ATMRunner/ implementation-class uk.man.atm.ATMRunner
    {
        ports:
            5fd251e1-ab35-4bde-b1ce-71ab65a9eb39/run/ provides 3338bbb5-7de1-4c0b-82a1-e424f3cd2cf3/IRun/,
            bc2581d5-939a-492f-a00f-8a24640ea72e/atmCli/ requires 4d4c0efa-7bd0-4b9c-9b27-0378285e1e11/IAtmCli/;
    }

}

