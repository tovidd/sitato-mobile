<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreatePegawaiTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('pegawai', function (Blueprint $table) {
            $table->increments('id_pegawai');
            $table->integer('id_role')->unsigned();
            $table->integer('id_cabang')->unsigned();
            $table->string('no_telepon_pegawai');
            $table->string('nama_pegawai');
            $table->string('alamat_pegawai');
            $table->double('gaji_pegawai');
            $table->string('username_pegawai');
            $table->string('sandi_pegawai');
            $table->timestamps();
            $table->softDeletes();
            //tipe data foreign dan references harus sama, makanya di kasih unsigned dulu
            //foreign
            $table->foreign('id_role')->references('id_role')->on('role');
            $table->foreign('id_cabang')->references('id_cabang')->on('cabang');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('pegawai');
    }
}
