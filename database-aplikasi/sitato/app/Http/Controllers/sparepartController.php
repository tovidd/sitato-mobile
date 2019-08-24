<?php

namespace sitato\Http\Controllers;

use sitato\sparepart;
use Illuminate\Http\Request;
use Image;

class sparepartController extends Controller
{
    public function index()
    {
        $s= sparepart::get();
        return response()->json($s, 200);
    }

    public function store(Request $request)
    {
        // $s->kode_sparepart= "TEST";
        // $s->nama_sparepart= "TEST";
        // $s->jenis_sparepart= "TEST";
        // $s->stok_sparepart= 1;
        // $s->stok_minimum_sparepart= 1;
        // $s->rak_sparepart= "TEST";
        // $s->harga_beli_sparepart= 1;
        // $s->harga_jual_sparepart= 1;
        // $s->merek_kendaraan_sparepart= "TEST";
        // $s->jenis_kendaraan_sparepart= "TEST";
        $s= new sparepart;
        $s->kode_sparepart= $request->kode_sparepart;
        $s->nama_sparepart= $request->nama_sparepart;
        $s->jenis_sparepart= $request->jenis_sparepart;
        $s->stok_sparepart= $request->stok_sparepart;
        $s->stok_minimum_sparepart= $request->stok_minimum_sparepart;
        $s->rak_sparepart= $request->rak_sparepart;
        $s->harga_beli_sparepart= $request->harga_beli_sparepart;
        $s->harga_jual_sparepart= $request->harga_jual_sparepart;
        $s->merek_kendaraan_sparepart= $request->merek_kendaraan_sparepart;
        $s->jenis_kendaraan_sparepart= $request->jenis_kendaraan_sparepart;
        $s->gambar_sparepart= 'files/gambar_sparepart/'.time()."-".$request->file('gambar_sparepart')->getClientOriginalName();

        $file= $request->file('gambar_sparepart');
        $img_name= time()."-".$file->getClientOriginalName();
        $file->move('files/gambar_sparepart', $img_name);
        $image= Image::make(sprintf('files/gambar_sparepart/%s', $img_name))->resize(200, 200)->save();

        $success= $s->save();
        if(!$success)
        {
            return response()->json('Error saving', 500);
        }
        else
        {
            return response()->json('Success', 200);
        }
    }

    public function show($id)
    {
        $s= sparepart::find($id);
        if(is_null($s))
        {
            return response()->json('Not found', 404);
        }
        else
        {
            return response()->json($s, 200);
        }
    }

    public function update(Request $request, $id)
    {
        $s= sparepart::find($id);
        if(!is_null($request->kode_sparepart))
        {
            $s->kode_sparepart= $request->kode_sparepart;
        }
        if(!is_null($request->nama_sparepart))
        {
            $s->nama_sparepart= $request->nama_sparepart;
        }
        if(!is_null($request->jenis_sparepart))
        {
            $s->jenis_sparepart= $request->jenis_sparepart;
        }
        if(!is_null($request->stok_sparepart))
        {
            $s->stok_sparepart= $request->stok_sparepart;
        }
        if(!is_null($request->stok_minimum_sparepart))
        {
            $s->stok_minimum_sparepart= $request->stok_minimum_sparepart;
        }
        if(!is_null($request->rak_sparepart))
        {
            $s->rak_sparepart= $request->rak_sparepart;
        }
        if(!is_null($request->harga_beli_sparepart))
        {
            $s->harga_beli_sparepart= $request->harga_beli_sparepart;
        }
        if(!is_null($request->harga_jual_sparepart))
        {
            $s->harga_jual_sparepart= $request->harga_jual_sparepart;
        }
        if(!is_null($request->merek_kendaraan_sparepart))
        {
            $s->merek_kendaraan_sparepart= $request->merek_kendaraan_sparepart;
        }
        if(!is_null($request->jenis_kendaraan_sparepart))
        {
            $s->jenis_kendaraan_sparepart= $request->jenis_kendaraan_sparepart;
        }
        if(!is_null($request->gambar_sparepart))
        {
            $s->gambar_sparepart= $request->gambar_sparepart;
        } 

        $success= $s->save();
        if(!$success)
        {
            return response()->json('Error updating', 500);
        }
        else
        {
            return response()->json('Success updating', 200);
        }
    }

    public function destroy($id)
    {
        $s= sparepart::find($id);
        if(is_null($s))
        {
            return response()->json('Not found', 404);
        }

        $success= $s->delete();
        if(!$success)
        {
            return response()->json('Error deleting', 500);
        }
        else
        {
            return response()->json('Success deleting', 200);
        }
    }
}
