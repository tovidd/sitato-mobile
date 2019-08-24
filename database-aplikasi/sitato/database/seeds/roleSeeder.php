<?php

use Illuminate\Database\Seeder;
use sitato\role;

class roleSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        role::create([
            'nama_role'=> 'Owner'
        ]);
        role::create([
            'nama_role'=> 'Customer service'
        ]);
        role::create([
            'nama_role'=> 'Kasir'
        ]);
        role::create([
            'nama_role'=> 'Montir'
        ]);
    }
}
