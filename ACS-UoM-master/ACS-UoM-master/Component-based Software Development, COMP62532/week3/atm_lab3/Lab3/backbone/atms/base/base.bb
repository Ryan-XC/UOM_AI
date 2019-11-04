stratum bb98adf0-87d9-49f8-814e-f73a187d0f34/base/
    parent 10a0d790-5479-4ff4-9b68-e3d98f2316cc/model/
     is-relaxed
    depends-on backbone
{
    interface 7e004de6-0807-4dec-8847-d7f035dcb8e8/ICardReader/ implementation-class uk.man.atm.ICardReader
    {
    }

    interface 081bb513-2878-498b-9f8b-5df769b1ab70/IBankServices/ implementation-class uk.man.atm.IBankServices
    {
    }

    interface 5d297631-4a0c-49a4-ba48-84e41bc5b675/IAccount/ implementation-class uk.man.atm.IAccount
    {
    }

    interface 9f7ed88c-52f9-4214-90b4-eb52b2ed7c70/IAtmCli/ implementation-class uk.man.atm.IAtmCli
    {
    }

    component 490cc05d-605e-47ff-a5bc-93955c62148d/CardReader/ implementation-class uk.man.atm.CardReader
    {
        ports:
            b7c02f85-7d34-44bd-a354-6adef6b62687/reader/ provides 7e004de6-0807-4dec-8847-d7f035dcb8e8/ICardReader/;
    }

    component 2c154417-0256-4de1-9530-936faf2e7e39/Bank/ implementation-class uk.man.atm.Bank
    {
        attributes:
            24ebbeb1-0187-4df5-9e07-b37711e65ce1/name/: String;
        ports:
            a28a63a3-95b5-4421-a515-a1730b10d851/service/ provides 081bb513-2878-498b-9f8b-5df769b1ab70/IBankServices/,
            92297c5b-615e-4d4a-86e7-10fbcccbdb19/accounts/[1 upto *] requires 5d297631-4a0c-49a4-ba48-84e41bc5b675/IAccount/;
    }

    component 1e1f4926-9073-4b9c-a077-59b883d8bc65/UI/ implementation-class uk.man.atm.UI
    {
        ports:
            96098260-1628-46bb-b8b3-b9b15cc8a73a/reader/ requires 7e004de6-0807-4dec-8847-d7f035dcb8e8/ICardReader/,
            a9efd4d1-5e82-4d8b-a2c7-7f0e1fb3a3b9/bank/ requires 081bb513-2878-498b-9f8b-5df769b1ab70/IBankServices/,
            bf7bcb22-fc8b-4937-a8b7-1673d3815af9/display/ provides 9f7ed88c-52f9-4214-90b4-eb52b2ed7c70/IAtmCli/;
    }

    component 5353549e-0dba-46c2-ac4f-9cdbef76bdce/Account/ implementation-class uk.man.atm.Account
    {
        attributes:
            439c609b-7dec-46e5-87ac-d9428095d12e/balance/: int,
            30dc6df2-dd41-492f-8712-c028d5823219/number/: int;
        ports:
            b013e919-5109-4d9a-a195-f7c7bb3ad076/accDetails/ provides 5d297631-4a0c-49a4-ba48-84e41bc5b675/IAccount/;
    }

    component 65976a96-10cf-4462-a470-4035cc40b275/ATMSystem/
    {
        ports:
            76115ee9-71bf-46ea-9788-e864950a7c65/run/;
        parts:
            fe644a7e-1930-4202-94e2-fe7d28d70fda/bank/: 2c154417-0256-4de1-9530-936faf2e7e39/Bank/
                slots:
                    24ebbeb1-0187-4df5-9e07-b37711e65ce1/name/ = "BigBank",
            2675f2af-392e-4899-ba0b-bf8e4d50b638/acc1/: 5353549e-0dba-46c2-ac4f-9cdbef76bdce/Account/
                slots:
                    439c609b-7dec-46e5-87ac-d9428095d12e/balance/ = 100
                    30dc6df2-dd41-492f-8712-c028d5823219/number/ = 1111,
            a1268f38-1803-4d67-a7cb-155a49185ca7/acc2/: 5353549e-0dba-46c2-ac4f-9cdbef76bdce/Account/
                slots:
                    439c609b-7dec-46e5-87ac-d9428095d12e/balance/ = 68584
                    30dc6df2-dd41-492f-8712-c028d5823219/number/ = 2222,
            67ce2c7c-6f16-45b7-b7b0-06f45a1da5d3/cli/: 1e1f4926-9073-4b9c-a077-59b883d8bc65/UI/,
            f01d0462-5c53-4792-8cde-785a49b9a692/cardReader/: 490cc05d-605e-47ff-a5bc-93955c62148d/CardReader/,
            eeb16025-d39e-4d1d-9fca-78a7b1e1e8ea: 42a08fdf-059a-4e44-8f30-6b299d5dd73a/ATMRunner/;
        connectors:
            bee27965-2913-47d8-a2ad-530d2c7470da/a/ joins 92297c5b-615e-4d4a-86e7-10fbcccbdb19/accounts/@fe644a7e-1930-4202-94e2-fe7d28d70fda/bank/ to b013e919-5109-4d9a-a195-f7c7bb3ad076/accDetails/@2675f2af-392e-4899-ba0b-bf8e4d50b638/acc1/,
            e0aaf599-ae74-439d-b856-974fe0d0af14/b/ joins 92297c5b-615e-4d4a-86e7-10fbcccbdb19/accounts/@fe644a7e-1930-4202-94e2-fe7d28d70fda/bank/ to b013e919-5109-4d9a-a195-f7c7bb3ad076/accDetails/@a1268f38-1803-4d67-a7cb-155a49185ca7/acc2/,
            354897c1-0436-4218-ae75-6b19dbc3522b joins a28a63a3-95b5-4421-a515-a1730b10d851/service/@fe644a7e-1930-4202-94e2-fe7d28d70fda/bank/ to a9efd4d1-5e82-4d8b-a2c7-7f0e1fb3a3b9/bank/@67ce2c7c-6f16-45b7-b7b0-06f45a1da5d3/cli/,
            fb82ec3e-bd30-4f12-803d-a6a309903be8 joins 96098260-1628-46bb-b8b3-b9b15cc8a73a/reader/@67ce2c7c-6f16-45b7-b7b0-06f45a1da5d3/cli/ to b7c02f85-7d34-44bd-a354-6adef6b62687/reader/@f01d0462-5c53-4792-8cde-785a49b9a692/cardReader/,
            b9c6007c-1cd0-4b35-8534-6b20aa143455 joins 9034910b-f3ca-4ec7-a1cc-53a6b614f40a/run/@eeb16025-d39e-4d1d-9fca-78a7b1e1e8ea to 76115ee9-71bf-46ea-9788-e864950a7c65/run/,
            8d2bb368-a6d6-4de2-b01c-e7b9ae25e3f4 joins bf7bcb22-fc8b-4937-a8b7-1673d3815af9/display/@67ce2c7c-6f16-45b7-b7b0-06f45a1da5d3/cli/ to d5dbf229-0ead-4400-987a-72b7a7bea489/show/@eeb16025-d39e-4d1d-9fca-78a7b1e1e8ea;
    }

    component 42a08fdf-059a-4e44-8f30-6b299d5dd73a/ATMRunner/ implementation-class uk.man.atm.ATMRunner
    {
        ports:
            9034910b-f3ca-4ec7-a1cc-53a6b614f40a/run/ provides IRun,
            d5dbf229-0ead-4400-987a-72b7a7bea489/show/ requires 9f7ed88c-52f9-4214-90b4-eb52b2ed7c70/IAtmCli/;
    }

}

