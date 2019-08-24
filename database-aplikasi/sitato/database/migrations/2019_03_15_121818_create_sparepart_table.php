<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateSparepartTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('sparepart', function (Blueprint $table) {
            $table->increments('id_sparepart');
            $table->string('kode_sparepart');
            $table->string('nama_sparepart');
            $table->string('jenis_sparepart');
            $table->integer('stok_sparepart');
            $table->integer('stok_minimum_sparepart');
            $table->string('rak_sparepart');
            $table->double('harga_beli_sparepart');
            $table->double('harga_jual_sparepart');
            $table->string('merek_kendaraan_sparepart');
            $table->string('jenis_kendaraan_sparepart');
            $table->string('gambar_sparepart');
            $table->timestamps();
            $table->softDeletes();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('sparepart');
    }
}
